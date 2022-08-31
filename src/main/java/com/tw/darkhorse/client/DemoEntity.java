package com.tw.darkhorse.client;

import javax.persistence.*;

@Entity
@Table(name = "Demo")
public class DemoEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;

    protected DemoEntity() {}

    public DemoEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
