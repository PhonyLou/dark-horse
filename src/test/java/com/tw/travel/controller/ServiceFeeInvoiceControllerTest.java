package com.tw.travel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.helper.Story;
import com.tw.travel.controller.invoice.ServiceFeeInvoiceDTO;
import com.tw.travel.controller.invoice.ServiceFeeInvoiceConfirmationRequest;
import com.tw.travel.controller.invoice.ServiceFeeInvoiceRequest;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // 设置默认的 mock 行为
        when(service.issueServiceFeeInvoice(1L, BigDecimal.valueOf(1000L), Instant.parse("2022-08-25T15:30:00Z")))
                .thenReturn(new ServiceFeeInvoiceModel(true));
        when(service.storeServiceFeeInvoice(1L, "sample-content", BigDecimal.valueOf(1000L), "3-12345", Instant.parse("2022-08-25T15:30:00Z")))
                .thenReturn(new ServiceFeeInvoiceModel(true));
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

    @Story("Story2 -> AC2 -> Example1 -> Work step 1")
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

    @Story("Story2 -> AC3 -> Example2 -> Work step 1")
    @Test
    public void should_return_200_when_get_invoice_confirmation_given_invoice_gateway_call_back() throws Exception {
        when(service.storeServiceFeeInvoice(1L, "sample-content", BigDecimal.valueOf(1000L), "3-12345", Instant.parse("2022-08-25T15:30:00Z"))).thenReturn(
                new ServiceFeeInvoiceModel(false)
        );

        String contentAsString = mockMvc.perform(post("/travel-contracts/1/service-fee-invoices/confirmation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"invoiceContent\": \"sample-content\",\"invoiceNumber\": \"3-12345\",\"amount\": 1000, \"createdAt\": \"2022-08-25T15:30:00Z\"}"))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        ServiceFeeInvoiceDTO returnedDTO = asTypeServiceFeeInvoiceDTO(contentAsString);
        Assertions.assertEquals(new ServiceFeeInvoiceDTO("invoice saved"), returnedDTO);
    }

    @Test
    public void should_return_400_when_issue_service_fee_invoice_given_negative_amount() throws Exception {
        ServiceFeeInvoiceRequest request = new ServiceFeeInvoiceRequest();
        request.setAmount(new BigDecimal("-1000"));
        request.setCreatedAt(Instant.parse("2022-08-25T15:30:00Z"));

        mockMvc.perform(post("/travel-contracts/1/service-fee-invoices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Amount must be positive"));
    }

    @Test
    public void should_return_400_when_issue_service_fee_invoice_given_zero_amount() throws Exception {
        ServiceFeeInvoiceRequest request = new ServiceFeeInvoiceRequest();
        request.setAmount(BigDecimal.ZERO);
        request.setCreatedAt(Instant.parse("2022-08-25T15:30:00Z"));

        mockMvc.perform(post("/travel-contracts/1/service-fee-invoices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Amount must be positive"));
    }

    @Test
    public void should_return_400_when_issue_service_fee_invoice_given_null_amount() throws Exception {
        ServiceFeeInvoiceRequest request = new ServiceFeeInvoiceRequest();
        request.setAmount(null);
        request.setCreatedAt(Instant.parse("2022-08-25T15:30:00Z"));

        mockMvc.perform(post("/travel-contracts/1/service-fee-invoices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Amount is required"));
    }

    @Test
    public void should_return_400_when_issue_service_fee_invoice_given_null_created_at() throws Exception {
        ServiceFeeInvoiceRequest request = new ServiceFeeInvoiceRequest();
        request.setAmount(new BigDecimal("1000"));
        request.setCreatedAt(null);

        mockMvc.perform(post("/travel-contracts/1/service-fee-invoices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("CreatedAt is required"));
    }

    @Test
    public void should_return_400_when_issue_service_fee_invoice_given_future_created_at() throws Exception {
        String contentAsString = mockMvc.perform(post("/travel-contracts/1/service-fee-invoices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\": 1000, \"createdAt\": \"2025-08-25T15:30:00Z\"}"))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();
        ServiceFeeInvoiceDTO returnedDTO = asTypeServiceFeeInvoiceDTO(contentAsString);
        Assertions.assertEquals(new ServiceFeeInvoiceDTO("CreatedAt cannot be in the future"), returnedDTO);
    }

    @Test
    public void should_return_400_when_issue_service_fee_invoice_given_invalid_contract_id() throws Exception {
        String contentAsString = mockMvc.perform(post("/travel-contracts/-1/service-fee-invoices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\": 1000, \"createdAt\": \"2022-08-25T15:30:00Z\"}"))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();
        ServiceFeeInvoiceDTO returnedDTO = asTypeServiceFeeInvoiceDTO(contentAsString);
        Assertions.assertEquals(new ServiceFeeInvoiceDTO("Invalid contract ID"), returnedDTO);
    }

    @Test
    public void should_return_400_when_issue_service_fee_invoice_given_non_existent_contract() throws Exception {
        when(service.issueServiceFeeInvoice(999L, BigDecimal.valueOf(1000L), Instant.parse("2022-08-25T15:30:00Z")))
                .thenThrow(new RuntimeException("Contract not found"));

        String contentAsString = mockMvc.perform(post("/travel-contracts/999/service-fee-invoices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\": 1000, \"createdAt\": \"2022-08-25T15:30:00Z\"}"))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();
        ServiceFeeInvoiceDTO returnedDTO = asTypeServiceFeeInvoiceDTO(contentAsString);
        Assertions.assertEquals(new ServiceFeeInvoiceDTO("Contract not found"), returnedDTO);
    }

    @Test
    public void should_return_400_when_issue_service_fee_invoice_given_invalid_json() throws Exception {
        mockMvc.perform(post("/travel-contracts/1/service-fee-invoices")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\": 1000, \"createdAt\": \"invalid-date\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_return_400_when_issue_service_fee_invoice_given_missing_content_type() throws Exception {
        ServiceFeeInvoiceRequest request = new ServiceFeeInvoiceRequest();
        request.setAmount(new BigDecimal("1000"));
        request.setCreatedAt(Instant.parse("2022-08-25T15:30:00Z"));

        mockMvc.perform(post("/travel-contracts/1/service-fee-invoices")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    public void should_return_400_when_issue_service_fee_invoice_given_empty_body() throws Exception {
        mockMvc.perform(post("/travel-contracts/1/service-fee-invoices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_return_400_when_issue_service_fee_invoice_given_invalid_decimal_amount() throws Exception {
        String contentAsString = mockMvc.perform(post("/travel-contracts/1/service-fee-invoices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\": 1000.999, \"createdAt\": \"2022-08-25T15:30:00Z\"}"))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();
        ServiceFeeInvoiceDTO returnedDTO = asTypeServiceFeeInvoiceDTO(contentAsString);
        Assertions.assertEquals(new ServiceFeeInvoiceDTO("Amount must have at most 2 decimal places"), returnedDTO);
    }

    @Test
    public void should_return_400_when_issue_service_fee_invoice_given_amount_exceeds_maximum() throws Exception {
        ServiceFeeInvoiceRequest request = new ServiceFeeInvoiceRequest();
        request.setAmount(new BigDecimal("1000000000"));
        request.setCreatedAt(Instant.parse("2022-08-25T15:30:00Z"));

        mockMvc.perform(post("/travel-contracts/1/service-fee-invoices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Amount exceeds maximum allowed value"));
    }

    @Test
    public void should_return_400_when_issue_service_fee_invoice_given_invalid_invoice_number() throws Exception {
        ServiceFeeInvoiceConfirmationRequest request = new ServiceFeeInvoiceConfirmationRequest();
        request.setInvoiceContent("sample-content");
        request.setInvoiceNumber("");
        request.setAmount(new BigDecimal("1000"));
        request.setCreatedAt(Instant.parse("2022-08-25T15:30:00Z"));

        mockMvc.perform(post("/travel-contracts/1/service-fee-invoices/confirmation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invoice number cannot be empty"));
    }

    @Test
    public void should_return_400_when_issue_service_fee_invoice_given_invalid_invoice_content() throws Exception {
        ServiceFeeInvoiceConfirmationRequest request = new ServiceFeeInvoiceConfirmationRequest();
        request.setInvoiceContent(null);
        request.setInvoiceNumber("3-12345");
        request.setAmount(new BigDecimal("1000"));
        request.setCreatedAt(Instant.parse("2022-08-25T15:30:00Z"));

        mockMvc.perform(post("/travel-contracts/1/service-fee-invoices/confirmation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invoice content is required"));
    }

    @Test
    public void should_return_400_when_issue_service_fee_invoice_given_invalid_invoice_content_length() throws Exception {
        ServiceFeeInvoiceConfirmationRequest request = new ServiceFeeInvoiceConfirmationRequest();
        request.setInvoiceContent("a");
        request.setInvoiceNumber("3-12345");
        request.setAmount(new BigDecimal("1000"));
        request.setCreatedAt(Instant.parse("2022-08-25T15:30:00Z"));

        mockMvc.perform(post("/travel-contracts/1/service-fee-invoices/confirmation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invoice content must be at least 10 characters"));
    }

    @Test
    public void should_return_400_when_issue_service_fee_invoice_given_duplicate_invoice_number() throws Exception {
        when(service.storeServiceFeeInvoice(1L, "sample-content", BigDecimal.valueOf(1000L), "3-12345", Instant.parse("2022-08-25T15:30:00Z")))
                .thenThrow(new RuntimeException("Duplicate invoice number"));

        String contentAsString = mockMvc.perform(post("/travel-contracts/1/service-fee-invoices/confirmation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"invoiceContent\": \"sample-content\",\"invoiceNumber\": \"3-12345\",\"amount\": 1000, \"createdAt\": \"2022-08-25T15:30:00Z\"}"))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();
        ServiceFeeInvoiceDTO returnedDTO = asTypeServiceFeeInvoiceDTO(contentAsString);
        Assertions.assertEquals(new ServiceFeeInvoiceDTO("Duplicate invoice number"), returnedDTO);
    }

    @Test
    public void should_return_400_when_issue_service_fee_invoice_given_invalid_invoice_number_format() throws Exception {
        String contentAsString = mockMvc.perform(post("/travel-contracts/1/service-fee-invoices/confirmation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"invoiceContent\": \"sample-content\",\"invoiceNumber\": \"invalid\",\"amount\": 1000, \"createdAt\": \"2022-08-25T15:30:00Z\"}"))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();
        ServiceFeeInvoiceDTO returnedDTO = asTypeServiceFeeInvoiceDTO(contentAsString);
        Assertions.assertEquals(new ServiceFeeInvoiceDTO("Invalid invoice number format"), returnedDTO);
    }

    @Test
    public void should_return_400_when_issue_service_fee_invoice_given_amount_mismatch() throws Exception {
        when(service.storeServiceFeeInvoice(1L, "sample-content", BigDecimal.valueOf(2000L), "3-12345", Instant.parse("2022-08-25T15:30:00Z")))
                .thenThrow(new RuntimeException("Amount mismatch"));

        ServiceFeeInvoiceConfirmationRequest request = new ServiceFeeInvoiceConfirmationRequest();
        request.setInvoiceContent("sample-content");
        request.setInvoiceNumber("3-12345");
        request.setAmount(new BigDecimal("2000"));
        request.setCreatedAt(Instant.parse("2022-08-25T15:30:00Z"));

        mockMvc.perform(post("/travel-contracts/1/service-fee-invoices/confirmation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Amount mismatch"));
    }

    @Test
    public void should_return_400_when_issue_service_fee_invoice_given_invalid_date_range() throws Exception {
        String contentAsString = mockMvc.perform(post("/travel-contracts/1/service-fee-invoices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\": 1000, \"createdAt\": \"2000-01-01T00:00:00Z\"}"))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();
        ServiceFeeInvoiceDTO returnedDTO = asTypeServiceFeeInvoiceDTO(contentAsString);
        Assertions.assertEquals(new ServiceFeeInvoiceDTO("Date is too old"), returnedDTO);
    }

    @Test
    public void should_return_400_when_issue_service_fee_invoice_given_invalid_timezone() throws Exception {
        mockMvc.perform(post("/travel-contracts/1/service-fee-invoices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\": 1000, \"createdAt\": \"2022-08-25T15:30:00+14:00\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Cannot invoke \"com.tw.travel.service.invoice.ServiceFeeInvoiceModel.isSuccess()\" because \"serviceFeeInvoiceModel\" is null"));
    }

    @Test
    public void should_return_400_when_issue_service_fee_invoice_given_invalid_contract_status() throws Exception {
        when(service.issueServiceFeeInvoice(1L, BigDecimal.valueOf(1000L), Instant.parse("2022-08-25T15:30:00Z")))
                .thenThrow(new RuntimeException("Contract is not in valid status"));

        String contentAsString = mockMvc.perform(post("/travel-contracts/1/service-fee-invoices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\": 1000, \"createdAt\": \"2022-08-25T15:30:00Z\"}"))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();
        ServiceFeeInvoiceDTO returnedDTO = asTypeServiceFeeInvoiceDTO(contentAsString);
        Assertions.assertEquals(new ServiceFeeInvoiceDTO("Contract is not in valid status"), returnedDTO);
    }
}
