package com.tw.darkhorse.controller;

import com.tw.darkhorse.service.DemoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

    private final DemoService demoService;

    public DemoController(final DemoService demoService) {
        this.demoService = demoService;
    }

    @GetMapping("/foo")
    final public ResponseEntity foo() {
        var demoModel = demoService.issueInvoice();
        System.out.println(demoModel);
        return ResponseEntity.ok(demoModel);
    }
}
