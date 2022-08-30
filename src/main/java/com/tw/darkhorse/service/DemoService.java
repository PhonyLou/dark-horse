package com.tw.darkhorse.service;

import com.tw.darkhorse.model.DemoModel;
import org.springframework.stereotype.Service;

@Service
public class DemoService {
    public DemoModel issueInvoice() {
        return new DemoModel("1", "myName");
    }
}
