package com.UserService.user.controller;

import com.UserService.user.dto.RegistrationDto;
import com.UserService.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
    private UserService userService;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationDto registrationDto){
        return ResponseEntity.ok(userService.register(registrationDto));
    }
}
