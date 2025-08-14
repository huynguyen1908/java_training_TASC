package org.example.controller;


import org.example.dto.request.UpdateUserRequest;
import org.example.dto.response.ApiResponse;
import org.example.dto.response.UserDTO;
import org.example.mapper.UserMapper;
import org.example.repository.UserRepository;
import org.example.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/{userId}/exists")
    public ResponseEntity<Boolean> checkUserExists(@PathVariable String userId) {
        return ResponseEntity.ok(userRepository.existsById(userId));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<Page<UserDTO>>> getUserList(Pageable pageable) {
        return ResponseEntity.ok().body(userService.getUserList(pageable));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<Object>> getUserDetail(@PathVariable String userId) {
        return ResponseEntity.ok().body(userService.getUserDetail(userId));

    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<ApiResponse<Object>> updateUserDetail(@PathVariable String userId, @RequestBody UpdateUserRequest request){
        return ResponseEntity.ok().body(userService.updateUserDetail(userId, request));
    }

    @PutMapping("/deactivate/{userId}")
    public ResponseEntity<ApiResponse<Object>> deactivateUser(@PathVariable String userId){
        return ResponseEntity.ok().body(userService.deactivateUser(userId));
    }

    @GetMapping("/{username}")
    public UserDTO getUserByUsername(@PathVariable String username){
        return userMapper.toDTO(userRepository.findByUsername(username));
    }

    @PostMapping("/upload-avatar/{userId}")
    public ResponseEntity<ApiResponse<Object>> uploadUserAvatar(@PathVariable String userId, @RequestParam MultipartFile files) {
        return ResponseEntity.ok().body(userService.uploadUserAvatar(userId, files));
    }

    @GetMapping("/avatar/{userId}")
    public ResponseEntity<ApiResponse<Object>> getUserAvatar(@PathVariable String userId) {
        return ResponseEntity.ok().body(userService.getUserAvatar(userId));
    }

}
