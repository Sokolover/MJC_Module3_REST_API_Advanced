package com.epam.esm.sokolov.converter;

import com.epam.esm.sokolov.dto.UserDTO;
import com.epam.esm.sokolov.model.user.User;
import org.springframework.stereotype.Service;

@Service
public class UserConverter {

    public UserDTO convert(User source) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(source.getId());
        userDTO.setUsername(source.getUsername());
        userDTO.setEmail(source.getEmail());
        userDTO.setRole(source.getRole());
        return userDTO;
    }

    User convert(UserDTO source) {
        User user = new User();
        user.setId(source.getId());
        user.setUsername(source.getUsername());
        user.setEmail(source.getEmail());
        user.setRole(source.getRole());
        return user;
    }
}
