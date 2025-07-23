package com.chrisV.tasktracker.backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class TestController {
    @GetMapping("/")
    public String hello() {
        return "Hello from spring-boot";
    }
}
