package com.UserService.user.controller;

import com.UserService.user.dto.RegistrationDTO;
import com.UserService.user.dto.UserResponseDTO;
import com.UserService.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody RegistrationDTO registrationDto){
        UserResponseDTO dto=userService.register(registrationDto);
        return ResponseEntity.ok(dto);
    }
}
