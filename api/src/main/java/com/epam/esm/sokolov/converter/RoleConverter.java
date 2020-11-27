package com.epam.esm.sokolov.converter;

import com.epam.esm.sokolov.dto.RoleDTO;
import com.epam.esm.sokolov.model.user.Role;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleConverter {

    Set<RoleDTO> convertRoleDtosFromRoles(Set<Role> roles) {
        if (roles == null) {
            return new HashSet<>();
        }
        return roles.stream()
                .map(RoleDTO::new)
                .collect(Collectors.toSet());
    }

    Set<Role> convertRolesFromRoleDtos(Set<RoleDTO> roleDTOS) {
        if (roleDTOS == null) {
            return new HashSet<>();
        }
        return roleDTOS.stream()
                .map(roleDTO -> new Role(roleDTO.getId(), roleDTO.getName()))
                .collect(Collectors.toSet());
    }
}
