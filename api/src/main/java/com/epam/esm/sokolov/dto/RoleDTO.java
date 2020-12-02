package com.epam.esm.sokolov.dto;

import com.epam.esm.sokolov.model.user.Role;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO extends RepresentationModel<UserDTO> {

    private Long id;
    @NotBlank(message = "is mandatory")
    private String name;

    public RoleDTO(Role role) {
        this.id = role.getId();
        this.name = role.getName();
    }
}
