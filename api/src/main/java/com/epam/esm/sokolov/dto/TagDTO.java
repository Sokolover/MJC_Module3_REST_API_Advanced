package com.epam.esm.sokolov.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TagDTO extends RepresentationModel<TagDTO> {

    private Long id;
    @NotBlank(message = "is mandatory")
    private String name;

}
