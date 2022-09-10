package com.tw.darkhorse.service;

import java.util.Objects;

public class DemoModel {
    private Long id;
    private String name;

    public DemoModel(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public DemoModel() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DemoModel demoModel = (DemoModel) o;
        return id.equals(demoModel.id) && name.equals(demoModel.name);
    }

    @Override
    public String toString() {
        return "DemoModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}