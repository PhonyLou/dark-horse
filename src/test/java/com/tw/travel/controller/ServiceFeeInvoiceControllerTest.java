package com.tw.travel.controller;

import com.tw.helper.Story;
import com.tw.travel.controller.invoice.ServiceFeeInvoiceDTO;
import com.tw.travel.service.invoice.ServiceFeeInvoiceModel;
import com.tw.travel.service.invoice.ServiceFeeInvoiceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;

import static com.tw.helper.DataHelper.asTypeServiceFeeInvoiceDTO;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ServiceFeeInvoiceControllerTest {

    @TestConfiguration
    public static class Config {
        @MockBean
        ServiceFeeInvoiceService service;
    }

    @Autowired
    private ServiceFeeInvoiceService service;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
    }

    @Story("Story2 -> AC1 -> Example1 -> Work step 1")
    @Test
    public void should_return_200_when_issue_service_fee_invoice_given_normal_case() throws Exception {
        when(service.issueServiceFeeInvoice(1L,
                BigDecimal.valueOf(1000L),
                Instant.parse("2022-08-25T15:30:00Z")))
                .thenReturn(new ServiceFeeInvoiceModel(true));

        String contentAsString = mockMvc.perform(post("/travel-contracts/1/service-fee-invoices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\": 1000, \"createdAt\": \"2022-08-25T15:30:00Z\"}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        ServiceFeeInvoiceDTO returnedDTO = asTypeServiceFeeInvoiceDTO(contentAsString);

        Assertions.assertEquals(new ServiceFeeInvoiceDTO("invoice request accepted"), returnedDTO);
    }

    @Test
    public void should_return_400_when_issue_service_fee_invoice_given_payment_not_success() throws Exception {
        when(service.issueServiceFeeInvoice(1L, BigDecimal.valueOf(1000L), Instant.parse("2022-08-25T15:30:00Z"))).thenReturn(
                new ServiceFeeInvoiceModel(false)
        );

        String contentAsString = mockMvc.perform(post("/travel-contracts/1/service-fee-invoices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\": 1000, \"createdAt\": \"2022-08-25T15:30:00Z\"}")).andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();
        ServiceFeeInvoiceDTO returnedDTO = asTypeServiceFeeInvoiceDTO(contentAsString);

        Assertions.assertEquals(new ServiceFeeInvoiceDTO("invoice request not accepted"), returnedDTO);
    }

    @Test
    public void should_return_200_when_get_invoice_confirmation_given_invoice_gateway_call_back() throws Exception {
        when(service.storeServiceFeeInvoice(1L, "sample-content", BigDecimal.valueOf(1000L), "3-12345")).thenReturn(
                new ServiceFeeInvoiceModel(false)
        );

        String contentAsString = mockMvc.perform(post("/travel-contracts/1/service-fee-invoices/confirmation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\": \"sample-content\",\"invoiceNumber\": \"3-12345\",\"amount\": 1000}"))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

//        Assertions.assertEquals("application/pdf", response.getContentType());
//        Assertions.assertEquals("sample-content", response.getContentAsString());
        ServiceFeeInvoiceDTO returnedDTO = asTypeServiceFeeInvoiceDTO(contentAsString);
        Assertions.assertEquals(new ServiceFeeInvoiceDTO("invoice saved"), returnedDTO);
    }

}
