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

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.esm.sokolov.constants.CommonAppConstants.CONVERT_ERROR_MESSAGE;

@Service
@NoArgsConstructor
@AllArgsConstructor(onConstructor_ = @Autowired)
public class RoleConverter {

    private ModelMapper modelMapper;

    public RoleDTO convert(Role source) {
        if (source == null) {
            throw new ConverterException(String.format(CONVERT_ERROR_MESSAGE, source.getClass().getSimpleName()));
        }
        return modelMapper.map(source, RoleDTO.class);
    }

    public Role convert(RoleDTO source) {
        if (source == null) {
            throw new ConverterException(String.format(CONVERT_ERROR_MESSAGE, source.getClass().getSimpleName()));
        }
        return modelMapper.map(source, Role.class);
    }

    public Set<RoleDTO> convertRolesToRoleDTOs(User source) {
        if (source.getRoles() == null) {
            return new HashSet<>();
        }
        return source.getRoles().stream()
                .map(this::convert)
                .collect(Collectors.toSet());
    }

    public Set<Role> convertRoleDTOsToRoles(UserDTO source) {
        if (source.getRoleDTOS() == null) {
            return new HashSet<>();
        }
        return source.getRoleDTOS().stream()
                .map(this::convert)
                .collect(Collectors.toSet());
    }
}
