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


}