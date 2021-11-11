package com.epam.esm.sokolov.converter;

import com.epam.esm.sokolov.dto.RoleDTO;
import com.epam.esm.sokolov.model.user.Role;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
@AllArgsConstructor(onConstructor_ = @Autowired)
public class RoleConverter {

    private ModelMapper modelMapper;

    public RoleDTO convert(Role source) {
        if (source == null) {
            return new RoleDTO();
        }
        return modelMapper.map(source, RoleDTO.class);
    }

    public Role convert(RoleDTO source) {
        if (source == null) {
            return new Role();
        }
        return modelMapper.map(source, Role.class);
    }

    public Set<RoleDTO> convertRolesToRoleDTOs(Set<Role> source) {
        if (source == null) {
            return new HashSet<>();
        }
        return source.stream()
                .map(this::convert)
                .collect(Collectors.toSet());
    }

    public Set<Role> convertRoleDTOsToRoles(Set<RoleDTO> source) {
        if (source == null) {
            return new HashSet<>();
        }
        return source.stream()
                .map(this::convert)
                .collect(Collectors.toSet());
    }
}
