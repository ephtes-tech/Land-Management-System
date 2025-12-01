package com.UserService.user.model;

import com.UserService.user.status.UpdateRequestStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class UserUpdateRequest {
        @Id
        @GeneratedValue
        private Long id;




        private Long userId;


        private String firstName;

        @Enumerated(EnumType.STRING)
        private UpdateRequestStatus status= UpdateRequestStatus.PENDING;


        private String middleName;


        private String lastName;


        private String nationalId;


        private String phoneNumber;



        private LocalDateTime updatedAt;

        private String address;
}
