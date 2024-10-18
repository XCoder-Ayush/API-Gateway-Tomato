package com.backend.tomato.controllers.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    private boolean isServiceHealthy = true;
    @GetMapping("/health")
    public String checkHealth() {
        return isServiceHealthy ? "Service is Healthy" : "Service is Down";
    }

}