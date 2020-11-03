package com.epam.esm.sokolov.dto;

import com.epam.esm.sokolov.model.Tag;

public class TagDTO {

    private Long id;
    private String name;

    public TagDTO() {

    }

    public TagDTO(Tag tag) {
        this.id = tag.getId();
        this.name = tag.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
