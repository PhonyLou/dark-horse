package com.tw.darkhorse.service;

import com.tw.darkhorse.outbound.database.DemoEntity;
import com.tw.darkhorse.outbound.database.DemoRepository;
import com.tw.darkhorse.outbound.messagequeue.MqClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DemoService {

    @Autowired
    private DemoRepository demoRepository;

    @Autowired
    private MqClient mqClient;

    public DemoModel save(final DemoModel demoModel) {
        DemoEntity demoEntity = new DemoEntity(demoModel.getId(), demoModel.getName());
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

    public String sendToMq(final Long id) {
        mqClient.send("test String from spring with input " + id);
        return "success";
    }
}
