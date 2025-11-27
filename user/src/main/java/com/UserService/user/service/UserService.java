package com.UserService.user.service;

import com.UserService.user.dto.RegistrationDTO;

import com.UserService.user.model.User;
import com.UserService.user.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;;
    public User register(RegistrationDTO dto) {
      /*  if (usernameExist(registrationDto.getUsername())){
            throw new RuntimeException("Username already exist");
        }
        if (emailExist(registrationDto.getEmail())){
            throw new RuntimeException("Email already exist");
        }
        if (phoneExist(registrationDto.getPhoneNumber())){
            throw new RuntimeException("Phone number already used");
        }

        if (NationalIdExist(registrationDto.getNationalId())){
            throw new RuntimeException("National id already used");
        }*/

        User user=dtoToEntity(dto);
        return userRepository.save(user);
    }
    public User dtoToEntity(RegistrationDTO dto){

        User user=new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setMiddleName(dto.getMiddleName());
        user.setLastName(dto.getLastName());
        user.setAddress(dto.getAddress());
        user.setNationalId(dto.getNationalId());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setCreatedAt(LocalDateTime.now());

        user.setRole("USER");
        user.setStatus("PENDING_VERIFICATION");
        return user;
    }

    public boolean usernameExist(String username){
        return userRepository.existsByUsername(username);
    }

    public boolean NationalIdExist(String NationalId){
        return userRepository.existsByNationalId(NationalId);
    }

    public boolean phoneExist(String phone){
        return userRepository.existsByPhoneNumber(phone);
    }

    public boolean emailExist(String email){
        return userRepository.existsByEmail(email);
    }
}
