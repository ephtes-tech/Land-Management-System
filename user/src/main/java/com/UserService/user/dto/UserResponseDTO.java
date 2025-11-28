package com.UserService.user.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserResponseDTO {
    private Long id;

    private String username;

    private String email;

    private String firstName;

    private String middleName;

    private String lastName;

    private String address;

    private String nationalId;

    private String status;

    private String role;


    private String phoneNumber;

}
