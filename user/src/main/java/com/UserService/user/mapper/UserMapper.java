package com.UserService.user.mapper;

import com.UserService.user.dto.RegistrationDTO;
import com.UserService.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper Instance= Mappers.getMapper(UserMapper.class);

    RegistrationDTO RegistrationDTOToUser(User user);
    User userDTOToUser(RegistrationDTO registrationDTO);

}
