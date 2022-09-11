package com.tw.travel.controller;

import com.tw.travel.service.ServiceFeePaymentModel;
import com.tw.travel.service.ServiceFeeService;
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

import static com.tw.travel.helper.DataHelper.asTypeServiceFeePaymentDTO;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ServiceFeeControllerTest {

    @TestConfiguration
    public static class Config {
        @MockBean
        ServiceFeeService serviceFeeService;
    }

    @Autowired
    private ServiceFeeService serviceFeeService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void given_balance_sufficient_when_paying_service_fee_then_return_200() throws Exception {
        when(serviceFeeService.payServiceFee(1L, BigDecimal.valueOf(1000L))).thenReturn(
                new ServiceFeePaymentModel(true)
        );

        String contentAsString = mockMvc.perform(post("/travel-contracts/1/service-fee-payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\": 1000}")).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        ServiceFeePaymentDTO returnedDTO = asTypeServiceFeePaymentDTO(contentAsString);

        Assertions.assertEquals(returnedDTO, new ServiceFeePaymentDTO("payment success"));
    }

}
