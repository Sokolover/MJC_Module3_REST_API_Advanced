package com.epam.esm.sokolov.dto;

import com.epam.esm.sokolov.model.Tag;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TagDTO extends RepresentationModel<OrderDTO> {

    private Long id;
    private String name;

    public TagDTO(Tag tag) {
        this.id = tag.getId();
        this.name = tag.getName();
    }
}
