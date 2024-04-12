package dev.project1backendspring.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class HealthCheckController {

    @GetMapping("/health")
    public String healthCheck() {
        return "Server is up and running!";
    }
}