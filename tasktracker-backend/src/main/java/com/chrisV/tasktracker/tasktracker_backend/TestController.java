package com.chrisV.tasktracker.tasktracker_backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/")
    public String hello() {
        return "Hello from spring-boot";
    }
}
