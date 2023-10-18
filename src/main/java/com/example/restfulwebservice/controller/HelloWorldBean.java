package com.example.restfulwebservice.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class HelloWorldBean {
    private String message;
    public HelloWorldBean(String message) {
        this.message =message;
    }
}
