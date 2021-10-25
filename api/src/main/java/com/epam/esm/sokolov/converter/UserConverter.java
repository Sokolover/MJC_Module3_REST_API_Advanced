package com.epam.esm.sokolov.converter;

import com.epam.esm.sokolov.dto.RoleDTO;
import com.epam.esm.sokolov.dto.UserDTO;
import com.epam.esm.sokolov.exception.ConverterException;
import com.epam.esm.sokolov.model.user.Role;
import com.epam.esm.sokolov.model.user.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.epam.esm.sokolov.constants.CommonAppConstants.CONVERT_ERROR_MESSAGE;
import static org.springframework.util.CollectionUtils.isEmpty;

@Service
@NoArgsConstructor
@AllArgsConstructor(onConstructor_ = @Autowired)
public class UserConverter {

    private RoleConverter roleConverter;
    private ModelMapper modelMapper;

    public UserDTO convert(User source) {
        if (source == null) {
            throw new ConverterException(String.format(CONVERT_ERROR_MESSAGE, source.getClass().getSimpleName()));
        }

        UserDTO result = modelMapper.map(source, UserDTO.class);

        if (!isEmpty(source.getRoles())) {
            Set<RoleDTO> roles = roleConverter.convertRolesToRoleDTOs(source);
            result.setRoleDTOS(roles);
        }

        return result;
    }

    public User convert(UserDTO source) {
        if (source == null) {
            throw new ConverterException(String.format(CONVERT_ERROR_MESSAGE, source.getClass().getSimpleName()));
        }

        User result = modelMapper.map(source, User.class);

        if (!isEmpty(source.getRoleDTOS())) {
            Set<Role> roles = roleConverter.convertRoleDTOsToRoles(source);
            result.setRoles(roles);
        }

        return result;
    }
}
