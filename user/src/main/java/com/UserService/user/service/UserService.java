package com.UserService.user.service;

import com.UserService.user.status.UpdateRequestStatus;
import com.UserService.user.dto.RegistrationDTO;

import com.UserService.user.dto.UpdateUserDto;
import com.UserService.user.dto.UserResponseDTO;
import com.UserService.user.exception.DuplicateResourceException;
import com.UserService.user.exception.NotFoundException;
import com.UserService.user.mapper.UserMapper;
import com.UserService.user.model.User;
import com.UserService.user.model.UserUpdateRequest;
import com.UserService.user.repo.UpdateRepo;
import com.UserService.user.repo.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UpdateRepo updateRepo;

    @Transactional
    public UserResponseDTO register(RegistrationDTO dto) {
        if (usernameExist(dto.getUsername())){
            throw new DuplicateResourceException("Username already exist");
        }
        if (emailExist(dto.getEmail())){
            throw new DuplicateResourceException("Email already exist");
        }
        if (phoneExist(dto.getPhoneNumber())){
            throw new DuplicateResourceException("Phone number already used");
        }

        if (nationalIdExist(dto.getNationalId())){
            throw new DuplicateResourceException("National id already used");
        }

        User user = UserMapper.toEntity(dto);
        User saved = userRepository.save(user);
        return UserMapper.toResponse(saved);
    }
    public List<UserResponseDTO> gellAllUser(){
        return userRepository.findAll().stream().map(UserMapper::toResponse)
                .collect(Collectors.toList());
    }

    public UserResponseDTO getUserById(Long id){
        User user=userRepository.findById(id)
                .orElseThrow(()->new NotFoundException("User Not Found"));
        return UserMapper.toResponse(user);
    }


    @Transactional
    public UserUpdateRequest updateUserDto(UpdateUserDto dto){
        UserUpdateRequest ur=new UserUpdateRequest();
        log.info("Created user update request");
        if(!userRepository.existsById(dto.getUserId())){
            throw new NotFoundException("User not found");
        }
        ur.setUserId(dto.getUserId());
        ur.setFirstName(dto.getFirstName());
        ur.setMiddleName(dto.getMiddleName());
        ur.setPhoneNumber(dto.getPhoneNumber());
        ur.setNationalId(dto.getNationalId());
        ur.setLastName(dto.getLastName());
        ur.setAddress(dto.getAddress());
        ur.setUpdatedAt(LocalDateTime.now());
        log.info("seted ur from dto");

        log.info("saved to update repo");
        return updateRepo.save(ur);
    }

    @Transactional
    public UserResponseDTO approveUserUpdate(Long requestId){
        UserUpdateRequest userUpdateRequest=updateRepo.findById(requestId).orElseThrow(
                ()->new NotFoundException("User Not Found"));
        if (userUpdateRequest.getStatus()== UpdateRequestStatus.APPROVED ||
                userUpdateRequest.getStatus()==UpdateRequestStatus.REJECTED){
            throw new IllegalStateException("Update request already processed");
        }
        User user=userRepository.findById(requestId).orElseThrow(()->
                new NotFoundException("User not found"));
        user.setAddress(userUpdateRequest.getAddress());
        user.setFirstName(userUpdateRequest.getFirstName());
        user.setLastName(userUpdateRequest.getLastName());
        user.setMiddleName(userUpdateRequest.getMiddleName());
        user.setPhoneNumber(userUpdateRequest.getPhoneNumber());
        user.setNationalId(userUpdateRequest.getNationalId());
        userRepository.save(user);

        userUpdateRequest.setStatus(UpdateRequestStatus.APPROVED);
        userUpdateRequest.setUpdatedAt(LocalDateTime.now());
        updateRepo.save(userUpdateRequest);
        return UserMapper.toResponse(user);

    }

    public List<UserUpdateRequest> getPending(){
        return updateRepo.findByStatus(UpdateRequestStatus.PENDING);
    }
    @Transactional
    public String rejectUpdateRequest(Long requestId) {
        UserUpdateRequest request = updateRepo.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Update request not found"));

        if (request.getStatus()== UpdateRequestStatus.APPROVED ||
                request.getStatus()==UpdateRequestStatus.REJECTED) {
            throw new IllegalStateException("Update request already processed");
        }

        request.setStatus(UpdateRequestStatus.REJECTED);
        request.setUpdatedAt(LocalDateTime.now());
        updateRepo.save(request);
        return "Rejected Successfully";
    }


    public boolean usernameExist(String username){
        return userRepository.existsByUsername(username);
    }

    public boolean nationalIdExist(String NationalId){
        return userRepository.existsByNationalId(NationalId);
    }

    public boolean phoneExist(String phone){
        return userRepository.existsByPhoneNumber(phone);
    }

    public boolean emailExist(String email){
        return userRepository.existsByEmail(email);
    }
}
