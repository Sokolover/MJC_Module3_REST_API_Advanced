package com.epam.esm.sokolov.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

import static com.epam.esm.sokolov.constants.CommonAppConstants.EMAIL_PATTERN;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserInDTO extends RepresentationModel<UserInDTO> {

    private Long id;
    @NotBlank(message = "is mandatory")
    private String username;
    @Email(regexp = EMAIL_PATTERN, message = "wrong email form")
    private String email;
    @NotBlank(message = "is mandatory")
    private String password;
    @NotBlank(message = "is mandatory")
    private String passwordConfirmation;
    private Set<RoleDTO> roleDTOS;

}
