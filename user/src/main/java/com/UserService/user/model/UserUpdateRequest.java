package com.UserService.user.model;

import com.UserService.user.UpdateRequestStatus;
import jakarta.persistence.*;
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

        @Enumerated(EnumType.STRING)
        private UpdateRequestStatus status= UpdateRequestStatus.PENDING;

        @Column(nullable = false)
        private String middleName;

        @Column(nullable = false)
        private String lastName;

        @Column(unique = true, nullable = false)
        private String nationalId;

        @Column(unique = true, nullable = false)
        private String phoneNumber;


        @Column(nullable = false)
        private LocalDateTime updatedAt;
        @Column(nullable = false)
        private String address;
}
