package org.example.dto.response;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class UserDTO {
    private String userId;
    private String username;
    private String fullName;
    private String address;
    private String email;
    private String phoneNumber;
    private String avatarUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isActive;
}
