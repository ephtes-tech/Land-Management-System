package com.UserService.user.mapper;

import com.UserService.user.dto.RegistrationDTO;
import com.UserService.user.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    RegistrationDTO toDto(User user);

    User toEntity(RegistrationDTO registrationDTO);
}
