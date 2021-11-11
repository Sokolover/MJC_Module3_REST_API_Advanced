package com.epam.esm.sokolov.converter;

import com.epam.esm.sokolov.dto.RoleDTO;
import com.epam.esm.sokolov.model.user.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Sokolov_SA
 * @created 26.10.2021
 */
@ExtendWith(MockitoExtension.class)
class RoleConverterTest {

    @InjectMocks
    private final RoleConverter roleConverter = new RoleConverter();
    @Spy
    private ModelMapper modelMapper;

    @Test
    void convertRoleToRoleDTO() {
        Role role = new Role();
        role.setId(1L);
        role.setName("USER");

        RoleDTO result = roleConverter.convert(role);
        assertEquals(role.getId(), result.getId());
        assertEquals(role.getName(), result.getName());
    }

    @Test
    void testRoleDTOToRole() {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(1L);
        roleDTO.setName("USER");

        Role result = roleConverter.convert(roleDTO);
        assertEquals(roleDTO.getId(), result.getId());
        assertEquals(roleDTO.getName(), result.getName());
    }
}