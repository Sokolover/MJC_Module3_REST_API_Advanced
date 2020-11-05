package com.epam.esm.sokolov.converter;

import com.epam.esm.sokolov.dto.UserDTO;
import com.epam.esm.sokolov.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserConverter {

    public UserDTO convert(User source) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(source.getId());
        userDTO.setUsername(source.getUsername());
        userDTO.setEmail(source.getEmail());
        return userDTO;
    }

    public User convert(UserDTO source) {
        User user = new User();
        user.setId(source.getId());
        user.setUsername(source.getUsername());
        user.setEmail(source.getEmail());
        return user;
    }
}
