//package org.example.client;
//
//import lombok.RequiredArgsConstructor;
//import org.example.dto.response.UserDTO;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import java.util.List;
//
//@FeignClient(name = "user-service")
//public interface UserClient {
//    @GetMapping("/api/users")
//    List<UserDTO> getUserList();
//
//}
