package org.example.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product-service")
public interface CartClient {
    @PostMapping("/api/cart/create")
    void createCart(@RequestParam("userId") String userId);
}
