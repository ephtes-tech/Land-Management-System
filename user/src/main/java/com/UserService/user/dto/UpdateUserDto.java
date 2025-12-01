package com.UserService.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserDto {
    private Long userId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String nationalId;
    private String phoneNumber;
    private String address;
}
