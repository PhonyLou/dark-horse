package com.tw.darkhorse.controller;

import com.tw.darkhorse.service.DemoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class DemoControllerTest {

    private DemoController demoController;
    private DemoService demoService = mock(DemoService.class);

    @BeforeEach
    public void setUp() {
        this.demoController = new DemoController(demoService);
    }

    @Test
    public void given_when_then() {
        ResponseEntity responseEntity = demoController.readReadings();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
