package com.tutorial.mapper;

import org.mapstruct.Mapper;

import com.tutorial.dto.UserDTO;
import com.tutorial.model.User;

@Mapper(componentModel = "spring", uses = { BookMapper.class, MapperUtils.class })
public interface UserMapper extends EntityConvert<User, UserDTO> {

}