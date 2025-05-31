package org.example.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UpdateUserRequest {
    private String username;
    private String password;
    private String fullName;
    private String address;
    private String email;
    private String phoneNumber;
    private String avatarUrl;
}
