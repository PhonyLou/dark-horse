package com.tw.darkhorse.controller;

import com.tw.darkhorse.service.DemoModel;
import com.tw.darkhorse.service.DemoService;
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

import static com.tw.darkhorse.helper.DataHelper.asJsonString;
import static com.tw.darkhorse.helper.DataHelper.asTypeDemo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class DemoControllerTest {

    @TestConfiguration
    public static class Config {
        @MockBean
        DemoService demoService;
    }

    @Autowired
    private DemoService demoService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void given_when_then() throws Exception {
        DemoModel expectedDemoModel = new DemoModel(123L, "firstname");

        when(demoService.save(expectedDemoModel)).thenReturn(expectedDemoModel);

        String contentAsString = mockMvc.perform(post("/demo/foo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expectedDemoModel))).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        DemoModel actualDemoModel = asTypeDemo(contentAsString);
        Assertions.assertEquals(expectedDemoModel, actualDemoModel);
    }

}
