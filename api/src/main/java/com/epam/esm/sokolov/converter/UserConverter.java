package com.epam.esm.sokolov.converter;

import com.epam.esm.sokolov.dto.RoleDTO;
import com.epam.esm.sokolov.dto.UserInDTO;
import com.epam.esm.sokolov.dto.UserOutDTO;
import com.epam.esm.sokolov.model.user.Role;
import com.epam.esm.sokolov.model.user.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

import static org.springframework.util.CollectionUtils.isEmpty;

@Service
@NoArgsConstructor
@AllArgsConstructor(onConstructor_ = @Autowired)
public class UserConverter {

    private RoleConverter roleConverter;
    private ModelMapper modelMapper;

    public UserOutDTO convert(User source) {
        if (source == null) {
            return new UserOutDTO();
        }

        UserOutDTO result = modelMapper.map(source, UserOutDTO.class);

        Set<Role> sourceRoles = source.getRoles();
        if (!isEmpty(sourceRoles)) {
            Set<RoleDTO> roles = roleConverter.convertRolesToRoleDTOs(sourceRoles);
            result.setRoleDTOS(roles);
        }

        return result;
    }

    public User convert(UserInDTO source) {
        if (source == null) {
            return new User();
        }

        User result = modelMapper.map(source, User.class);

        Set<RoleDTO> sourceRoleDTOs = source.getRoleDTOS();
        if (!isEmpty(sourceRoleDTOs)) {
            Set<Role> roles = roleConverter.convertRoleDTOsToRoles(sourceRoleDTOs);
            result.setRoles(roles);
        }

        return result;
    }
}
