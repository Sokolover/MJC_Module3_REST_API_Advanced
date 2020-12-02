package com.epam.esm.sokolov.converter;

import com.epam.esm.sokolov.dto.RoleDTO;
import com.epam.esm.sokolov.dto.UserDTO;
import com.epam.esm.sokolov.model.user.Role;
import com.epam.esm.sokolov.model.user.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@NoArgsConstructor
@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class UserConverter {

    private RoleConverter roleConverter;

    public UserDTO convert(User source) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(source.getId());
        userDTO.setUsername(source.getUsername());
        userDTO.setEmail(source.getEmail());
        Set<Role> roles = source.getRoles();
        if (roles != null) {
            userDTO.setRoleDTOS(roleConverter.convertRoleDtosFromRoles(roles));
        }
        return userDTO;
    }

    public User convert(UserDTO source) {
        User user = new User();
        user.setId(source.getId());
        user.setUsername(source.getUsername());
        user.setEmail(source.getEmail());
        user.setPassword(source.getPassword());
        Set<RoleDTO> roleDTOS = source.getRoleDTOS();
        if (roleDTOS != null) {
            user.setRoles(roleConverter.convertRolesFromRoleDtos(roleDTOS));
        }
        return user;
    }
}
