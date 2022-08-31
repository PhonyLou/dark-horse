package com.tw.darkhorse.controller;

import com.tw.darkhorse.service.DemoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        return ResponseEntity.ok(demoModel);
    }

    @GetMapping("/bar/{id}")
    final public ResponseEntity bar(@PathVariable final Long id) {
        var demoModel = demoService.findDemoBy(id);
        if (demoModel.isPresent()) {
            return ResponseEntity.ok(demoModel.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
