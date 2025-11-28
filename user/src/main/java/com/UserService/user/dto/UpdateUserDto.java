package com.UserService.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserDto {
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Middle name is required")
    private String middleName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Pattern(
            regexp = "^[0-9]{12}$",
            message = "National ID must be exactly 12 digits"
    )
    private String nationalId;

    @Pattern(
            regexp = "^\\+251(7|9)[0-9]{8}$",
            message = "Phone number must start with +2517 or +2519 and have 12 digits"
    )
    private String phoneNumber;


    @NotBlank(message = "Last name is required")
    private String address;
}
