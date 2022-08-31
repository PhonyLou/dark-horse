package com.tw.darkhorse.controller;

import com.tw.darkhorse.service.DemoModel;
import com.tw.darkhorse.service.DemoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/demo")
public class DemoController {

    private final DemoService demoService;

    public DemoController(final DemoService demoService) {
        this.demoService = demoService;
    }

    @PostMapping("/foo")
    final public ResponseEntity foo(@RequestBody DemoModel i) {
        var demoModel = demoService.save(i);
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

    @GetMapping("/mq/{id}")
    final public ResponseEntity sendMq(@PathVariable final Long id) {
        return ResponseEntity.ok(demoService.sendToMq(id));
    }
}
