package com.tw.travel.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.travel.controller.ServiceFeePaymentDTO;

public abstract class DataHelper {
    static ObjectMapper mapper = new ObjectMapper();

    public static String asJsonString(Object o) throws JsonProcessingException {
        return mapper.writeValueAsString(o);
    }

    public static ServiceFeePaymentDTO asTypeServiceFeePaymentDTO(String s) throws JsonProcessingException {
        return mapper.readValue(s, ServiceFeePaymentDTO.class);
    }
}
