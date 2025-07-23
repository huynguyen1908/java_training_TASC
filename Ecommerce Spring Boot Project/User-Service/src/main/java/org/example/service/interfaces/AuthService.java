package org.example.service.interfaces;


import org.example.dto.request.LoginRequest;
import org.example.dto.request.RegisterRequest;
import org.example.dto.response.ApiResponse;
import org.example.dto.response.LoginResponse;

public interface AuthService {
    ApiResponse<Object> register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
    void logout();
    void forgotPassword();
    void changePassword(String userId, String newPassword);
}
