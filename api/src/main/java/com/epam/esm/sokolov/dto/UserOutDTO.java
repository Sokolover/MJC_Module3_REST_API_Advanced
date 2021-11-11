package com.epam.esm.sokolov.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserOutDTO extends RepresentationModel<UserOutDTO> {

    private Long id;
    private String username;
    private String email;
    private Set<RoleDTO> roleDTOS;

}
