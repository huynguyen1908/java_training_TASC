package org.example.service.interfaces;


import org.example.dto.request.UpdateUserRequest;
import org.example.dto.response.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getUserList();
    UserDTO getUserDetail(String userId);
    UserDTO updateUserDetail(String userId, UpdateUserRequest request);
    UserDTO deactivateUser(String userId);
}
