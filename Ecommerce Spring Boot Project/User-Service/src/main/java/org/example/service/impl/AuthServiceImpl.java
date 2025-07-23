package org.example.service.impl;

import org.example.client.CartClient;
import org.example.dto.request.LoginRequest;
import org.example.dto.request.RegisterRequest;
import org.example.dto.response.ApiResponse;
import org.example.dto.response.LoginResponse;
import org.example.entity.User;
import org.example.enums.Role;
import org.example.exception.AppException;
import org.example.exception.StatusCode;
import org.example.mapper.UserMapper;
import org.example.repository.UserRepository;
import org.example.service.interfaces.AuthService;
import org.example.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CartClient cartClient;

    @Override
    public LoginResponse login(LoginRequest request){
        User user = userRepository.findByUsername(request.getUsername());
        if (user == null) {
            throw new RuntimeException("Invalid username");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        String jwtToken = jwtUtil.generateToken(user.getUserId(), user.getUsername(), user.getRole().name());
        return new LoginResponse(
                jwtToken,
                userMapper.toDTO(user)
        );
    }

    @Override
    @Transactional
    public ApiResponse<Object> register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername().toLowerCase()) != null) {
            throw new AppException(StatusCode.DATA_EXISTED, "Username already exists", "username");
        }
        if (userRepository.findByPhoneNumber(request.getPhoneNumber()) != null) {
            throw new AppException(StatusCode.DATA_EXISTED, "Phone number already exists", "phoneNumber");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(StatusCode.DATA_EXISTED, "Email already exists", "email");
        }

        User user = new User();
        user.setUsername(request.getUsername().trim().toLowerCase());
        user.setPassword(passwordEncoder.encode(request.getPassword().trim()));
        user.setFullName(request.getFullName().trim());
        user.setAddress(request.getAddress().trim());
        user.setEmail(request.getEmail().trim());
        user.setPhoneNumber(request.getPhoneNumber().trim());
        user.setRole(Role.USER);
        user.setCreatedAt(LocalDateTime.now());
        user.setActive(true);
        userRepository.save(user);

        cartClient.createCart(user.getUserId());
        return ApiResponse.builder()
                .message("Register successful!")
                .data(null)
                .build();
    }

    @Override
    public void logout(){
        System.out.println("Logout successful");
    }

    @Override
    public void forgotPassword() {

    }

    @Override
    public void changePassword(String userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new RuntimeException("New password must be different from the old password");
        } else {
            String encodedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encodedPassword);
            userRepository.save(user);
            System.out.println("Change password successful");
        }
    }

}
