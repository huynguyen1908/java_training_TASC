package org.example.controller;


import org.example.dto.request.UpdateUserRequest;
import org.example.dto.response.UserDTO;
import org.example.mapper.UserMapper;
import org.example.repository.UserRepository;
import org.example.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<UserDTO>> getUserList(){
        return ResponseEntity.ok().body(userService.getUserList());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserDetail(@PathVariable String userId) {
        return ResponseEntity.ok().body(userService.getUserDetail(userId));

    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDTO> updateUserDetail(@PathVariable String userId, @RequestBody UpdateUserRequest request){
        return ResponseEntity.ok().body(userService.updateUserDetail(userId, request));
    }

    @PutMapping("/deactivate/{userId}")
    public ResponseEntity<UserDTO> deactivateUser(@PathVariable String userId){
        return ResponseEntity.ok().body(userService.deactivateUser(userId));
    }

    @GetMapping("/{username}")
    public UserDTO getUserByUsername(@PathVariable String username){
        return userMapper.toDTO(userRepository.findByUsername(username));
    }
}
