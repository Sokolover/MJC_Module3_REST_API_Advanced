package com.epam.esm.sokolov.converter;

import com.epam.esm.sokolov.dto.TagDTO;
import com.epam.esm.sokolov.model.Tag;

import java.util.Set;
import java.util.stream.Collectors;

class TagConverter {

    private TagConverter() {

    }

    static Set<TagDTO> convertTagsDtosFromTag(Set<Tag> tags) {
        return tags.stream()
                .map(TagDTO::new)
                .collect(Collectors.toSet());
    }

    static Set<Tag> convertTagsFromTagDtos(Set<TagDTO> tagDTOS) {
        return tagDTOS.stream()
                .map(tagDTO -> new Tag(tagDTO.getId(), tagDTO.getName()))
                .collect(Collectors.toSet());
    }
}
