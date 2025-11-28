package com.UserService.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class UserUpdateRequest {
        @Id
        @GeneratedValue
        private Long id;

        @Column(unique = true, nullable = false)
        private Long userId;

        @Column(nullable = false)
        private String firstName;

        @Column(nullable = false)
        private String middleName;

        @Column(nullable = false)
        private String lastName;

        @Column(unique = true, nullable = false)
        private String nationalId;

        @Column(unique = true, nullable = false)
        private String phoneNumber;


        @Column(nullable = false)
        private LocalDateTime localDateTime;
        @Column(nullable = false)
        private String address;
}
