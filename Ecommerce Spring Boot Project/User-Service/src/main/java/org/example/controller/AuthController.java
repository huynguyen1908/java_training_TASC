package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.request.LoginRequest;
import org.example.dto.request.RegisterRequest;
import org.example.dto.response.ApiResponse;
import org.example.dto.response.LoginResponse;
import org.example.service.interfaces.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok().body(authService.login(request));
    }

    @PostMapping("/register")
    public ApiResponse<?> register(@Valid @RequestBody RegisterRequest request){
        return authService.register(request);
    }
    @PostMapping("/change-password/{userId}")
    public ResponseEntity<?> changePassword(@PathVariable String userId, @RequestParam String newPassword){
        authService.changePassword(userId, newPassword);
        return ResponseEntity.ok("Change password successful");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(){
        authService.logout();
        return ResponseEntity.ok("Logout successful");
    }
}
