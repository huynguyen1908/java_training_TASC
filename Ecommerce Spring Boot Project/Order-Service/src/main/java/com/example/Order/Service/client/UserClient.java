package com.example.Order.Service.client;

import com.example.Order.Service.dto.response.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-SERVICE")
public interface UserClient {
    @GetMapping("/api/user/{username}")
    UserDTO getUserByUsername(@PathVariable String username);
}
