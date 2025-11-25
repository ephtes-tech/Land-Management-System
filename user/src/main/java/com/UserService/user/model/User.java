package com.UserService.user.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long userId;

    @Column(unique = true , nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Email(message = "Enter a valid email")
    @NotBlank(message = "Email is required")
    @Column(unique = true, nullable = false )
    private String email;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String middleName;

    @Column(nullable = false)
    private String lastName;

    @Pattern(
            regexp = "^\\+251(7|9)[0-9]{8}$",
            message = "Phone number must start with +2517 or +2519 and have 12 digits"
    )
    @Column(unique = true, nullable = false )
    private String phoneNumber;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String role="USER";

    @Column(nullable = false)
    private String status="PENDING_VERIFICATION";

    @Column(nullable = false)
    private LocalDateTime createdAt=LocalDateTime.now();

    @Pattern(
            regexp = "^[0-9]{12}$",
            message = "National ID must be exactly 12 digits"
    )
    @Column(unique = true, nullable = false, length = 12)
    private String nationalId;


}
