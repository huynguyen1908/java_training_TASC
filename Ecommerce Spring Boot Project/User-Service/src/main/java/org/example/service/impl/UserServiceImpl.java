package org.example.service.impl;


import org.example.dto.request.UpdateUserRequest;
import org.example.dto.response.UserDTO;
import org.example.entity.User;
import org.example.mapper.UserMapper;
import org.example.repository.UserRepository;
import org.example.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> getUserList() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .toList();
    }
    @Override
    public UserDTO getUserDetail(String userId){
        return userRepository.findById(userId)
                .map(userMapper::toDTO).orElseThrow(()-> new RuntimeException("User not found"));
    }
    @Override
    public UserDTO updateUserDetail(String userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found!"));
        userMapper.updateUserFromRequest(request, user);
        if (request.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
        return userMapper.toDTO(user);
    }
    @Override
    public UserDTO deactivateUser(String userId){
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found!"));
        user.setActive(false);
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

}
