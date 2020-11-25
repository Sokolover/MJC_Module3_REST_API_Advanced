package com.epam.esm.sokolov.dto;

import com.epam.esm.sokolov.model.user.Role;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO extends RepresentationModel<UserDTO> {

    private Long id;
    private String username;
    private String email;
    private Role role;
}
