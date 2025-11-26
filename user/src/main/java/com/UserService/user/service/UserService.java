package com.UserService.user.service;

import com.UserService.user.dto.RegistrationDto;
import com.UserService.user.model.User;
import com.UserService.user.repo.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    User user=new User();


    public String register(RegistrationDto registrationDto) {
        return userRepository.save();
    }
}
