package com.UserService.user.service;

import com.UserService.user.dto.RegistrationDTO;
import com.UserService.user.mapper.UserMapper;
import com.UserService.user.model.User;
import com.UserService.user.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    public User register(RegistrationDTO registrationDto) {
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
        User user= userMapper.toEntity(registrationDto);

        return userRepository.save(user);
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
