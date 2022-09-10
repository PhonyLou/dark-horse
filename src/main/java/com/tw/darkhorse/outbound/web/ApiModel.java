package com.tw.darkhorse.outbound.web;

public class ApiModel {
    private String name;

    public ApiModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
