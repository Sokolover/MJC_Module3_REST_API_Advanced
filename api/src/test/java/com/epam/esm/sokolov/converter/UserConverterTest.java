package com.epam.esm.sokolov.converter;

import com.epam.esm.sokolov.dto.RoleDTO;
import com.epam.esm.sokolov.dto.UserDTO;
import com.epam.esm.sokolov.model.user.Role;
import com.epam.esm.sokolov.model.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Sokolov_SA
 * @created 26.10.2021
 */
@ExtendWith(MockitoExtension.class)
class UserConverterTest {

    @InjectMocks
    private final UserConverter userConverter = new UserConverter();
    @Spy
    private ModelMapper modelMapper;
    @Spy
    private RoleConverter roleConverter;

    @Test
    void convertUserToUserDTO() {
        User user = new User();
        user.setId(1L);
        user.setUsername("username");
        user.setPassword("qwe123");
        user.setEmail("ss@ss.com");
        Set<Role> roles = getRoles();
        user.setRoles(roles);

        Mockito.doReturn(getRoleDTOs()).when(roleConverter).convertRolesToRoleDTOs(roles);

        UserDTO result = userConverter.convert(user);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getPassword(), result.getPassword());
        assertEquals(user.getEmail(), result.getEmail());
    }

    @Test
    void convertUserDTOToUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setUsername("username");
        userDTO.setPassword("qwe123");
        userDTO.setEmail("ss@ss.com");
        Set<RoleDTO> roleDTOs = getRoleDTOs();
        userDTO.setRoleDTOS(roleDTOs);

        Mockito.doReturn(getRoles()).when(roleConverter).convertRoleDTOsToRoles(roleDTOs);

        User result = userConverter.convert(userDTO);
        assertEquals(userDTO.getId(), result.getId());
        assertEquals(userDTO.getUsername(), result.getUsername());
        assertEquals(userDTO.getPassword(), result.getPassword());
        assertEquals(userDTO.getEmail(), result.getEmail());
    }

    private Set<Role> getRoles() {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(1L, "USER"));
        roles.add(new Role(2L, "ADMIN"));
        return roles;
    }

    private Set<RoleDTO> getRoleDTOs() {
        Set<RoleDTO> roleDTOs = new HashSet<>();
        roleDTOs.add(new RoleDTO(1L, "USER"));
        roleDTOs.add(new RoleDTO(2L, "ADMIN"));
        return roleDTOs;
    }
}