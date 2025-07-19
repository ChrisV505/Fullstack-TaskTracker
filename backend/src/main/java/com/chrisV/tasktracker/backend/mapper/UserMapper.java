package com.chrisV.tasktracker.backend.mapper;

import com.chrisV.tasktracker.backend.dto.UserDTO;
import com.chrisV.tasktracker.backend.model.User;

public class UserMapper {

    public static UserDTO fromEntitySimpleUser(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }

    //convert from DTO to entity
    public static User toEntityUser(UserDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        return user;
    }

    // update existing user using the DTO
    public static void updateEntityUser(User user, UserDTO dto) {
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
    }
}