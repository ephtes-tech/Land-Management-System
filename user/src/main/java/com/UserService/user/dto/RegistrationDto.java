package com.UserService.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RegistrationDto {
    private String username;

    @Email(message = "Enter a valid email")
    @NotBlank(message = "Email is required")
    private String email;

    private String firstName;
    private String middleName;
    private String lastName;
    private String address;

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

}
