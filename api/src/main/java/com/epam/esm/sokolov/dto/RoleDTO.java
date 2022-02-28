package com.epam.esm.sokolov.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO extends RepresentationModel<RoleDTO> {

    private Long id;
    @NotBlank(message = "is mandatory")
    private String name;

}
