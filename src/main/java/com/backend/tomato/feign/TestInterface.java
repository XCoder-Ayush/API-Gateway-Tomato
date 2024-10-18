package com.backend.tomato.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@FeignClient("ORDER-SERVICE")
public interface TestInterface {

    @GetMapping("/api/v1/orders")
    List<Map<String,Object>> getAllOrders();

    @GetMapping("orders/health")
    String checkHealth();
}
