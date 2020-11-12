package com.epam.esm.sokolov.converter;

import com.epam.esm.sokolov.dto.TagDTO;
import com.epam.esm.sokolov.model.Tag;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TagConverter {

    public TagDTO convert(Tag source) {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setId(source.getId());
        tagDTO.setName(source.getName());
        return tagDTO;
    }

    Set<TagDTO> convertTagDtosFromTags(Set<Tag> tags) {
        if (tags == null) {
            return new HashSet<>();
        }
        return tags.stream()
                .map(TagDTO::new)
                .collect(Collectors.toSet());
    }

    Set<Tag> convertTagsFromTagDtos(Set<TagDTO> tagDTOS) {
        if (tagDTOS == null) {
            return new HashSet<>();
        }
        return tagDTOS.stream()
                .map(tagDTO -> new Tag(tagDTO.getId(), tagDTO.getName()))
                .collect(Collectors.toSet());
    }
}
