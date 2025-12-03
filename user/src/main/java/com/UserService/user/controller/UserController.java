package com.UserService.user.controller;

import com.UserService.user.dto.RegistrationDTO;
import com.UserService.user.dto.UpdateUserDto;
import com.UserService.user.dto.UserResponseDTO;
import com.UserService.user.model.UserUpdateRequest;
import com.UserService.user.service.KeyCloakService;
import com.UserService.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping("/updateRequest")
    public ResponseEntity<UserUpdateRequest> updateProfile(@RequestBody UpdateUserDto dto){
        return ResponseEntity.ok(userService.updateUserDto(dto));
    }



    @PutMapping("/approve/{id}")
    public ResponseEntity<UserResponseDTO> approve(@PathVariable Long id){
        return ResponseEntity.ok(userService.approveUserUpdate(id));
    }
    @PutMapping("/rejected/{id}")
    public ResponseEntity<String> reject(@PathVariable Long id){
        return ResponseEntity.ok(userService.rejectUpdateRequest(id));
    }
    @GetMapping("/getUsers")
    public ResponseEntity<List<UserResponseDTO>> allUser(){
        log.info("calling get all user");
        return ResponseEntity.ok(userService.gellAllUser());
    }
    @GetMapping("/listPending")
    public ResponseEntity<List<UserUpdateRequest>> pendings(){
        return ResponseEntity.ok(userService.getPending());
    }

}
