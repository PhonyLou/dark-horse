package com.tw.darkhorse.controller;

import com.tw.darkhorse.service.model.DemoModel;
import com.tw.darkhorse.service.DemoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DemoControllerTest {

    private DemoController demoController;
    private final DemoService demoService = mock(DemoService.class);

    @BeforeEach
    public void setUp() {
        this.demoController = new DemoController(demoService);
    }

    @Test
    public void given_when_then() {
        DemoModel expectedDemoModel = new DemoModel(123L, "firstname");
        when(demoService.issueInvoice()).thenReturn(expectedDemoModel);

        var responseEntity = demoController.foo();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat((DemoModel) responseEntity.getBody()).isEqualTo(expectedDemoModel);
    }

}
