package com.UserService.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RegistrationDTO {
    @NotBlank(message = "Username require ")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20")
    private String username;

    @Email(message = "Enter a valid email")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Middle name is required")
    private String middleName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Address is required")
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
