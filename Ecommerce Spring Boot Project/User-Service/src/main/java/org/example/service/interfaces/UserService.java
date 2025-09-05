package org.example.service.interfaces;


import org.example.dto.request.UpdateUserRequest;
import org.example.dto.response.ApiResponse;
import org.example.dto.response.PageResponse;
import org.example.dto.response.UserDTO;
import org.example.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface UserService {
    ApiResponse<Object> getUserList(Pageable pageable);
    ApiResponse<Object> getUserDetail(String userId);
    ApiResponse<Object> updateUserDetail(String userId, UpdateUserRequest request);
    ApiResponse<Object> deactivateUser(String userId);
    ApiResponse<Object> uploadUserAvatar(String userId, MultipartFile files);
    ApiResponse<Object> getUserAvatar(String userId);
}
