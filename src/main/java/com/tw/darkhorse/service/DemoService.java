package com.tw.darkhorse.service;

import com.tw.darkhorse.client.DemoEntity;
import com.tw.darkhorse.client.DemoRepository;
import com.tw.darkhorse.model.DemoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DemoService {

    @Autowired
    private DemoRepository demoRepository;

    public DemoModel issueInvoice() {
        var demoEntity = new DemoEntity(123L, "myName");
        DemoEntity savedEntity = demoRepository.save(demoEntity);
        return convertToModel(savedEntity);
    }

    public Optional<DemoModel> findDemoBy(final Long id) {
        Optional<DemoEntity> demoEntity = demoRepository.findById(id);
        return demoEntity.map(this::convertToModel);
    }

    private DemoModel convertToModel(final DemoEntity entity) {
        return new DemoModel(entity.getId(), entity.getName());
    }
}
