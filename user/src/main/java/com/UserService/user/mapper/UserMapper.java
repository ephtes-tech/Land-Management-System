package com.UserService.user.mapper;

import com.UserService.user.dto.RegistrationDTO;
import com.UserService.user.dto.UserResponseDTO;
import com.UserService.user.model.User;

import java.time.LocalDateTime;

public class UserMapper {
    private UserMapper() {}

    public static User toEntity(RegistrationDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setPassword(dto.getPassword());
        user.setMiddleName(dto.getMiddleName());
        user.setLastName(dto.getLastName());
        user.setAddress(dto.getAddress());
        user.setNationalId(dto.getNationalId());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setRole("USER");
        user.setStatus("PENDING_VERIFICATION");
        user.setCreatedAt(LocalDateTime.now());
        return user;
    }

    public static UserResponseDTO toResponse(User user) {
        return UserResponseDTO.builder()
                .id(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .middleName(user.getMiddleName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .role(user.getRole())
                .status(user.getStatus())
                .nationalId(user.getNationalId())
                .password(user.getPassword())
                .localDateTime(user.getCreatedAt())
                .build();
    }
}
