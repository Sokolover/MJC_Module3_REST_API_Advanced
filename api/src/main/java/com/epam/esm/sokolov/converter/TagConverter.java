package com.epam.esm.sokolov.converter;

import com.epam.esm.sokolov.dto.TagDTO;
import com.epam.esm.sokolov.model.Tag;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
@AllArgsConstructor(onConstructor_ = @Autowired)
public class TagConverter {

    private ModelMapper modelMapper;

    public TagDTO convert(Tag source) {
        if (source == null) {
            return new TagDTO();
        }
        return modelMapper.map(source, TagDTO.class);
    }

    public Tag convert(TagDTO source) {
        if (source == null) {
            return new Tag();
        }
        return modelMapper.map(source, Tag.class);
    }

    public Set<TagDTO> convertTagsToTagDTOs(Set<Tag> tags) {
        if (tags == null) {
            return new HashSet<>();
        }
        return tags.stream()
                .map(this::convert)
                .collect(Collectors.toSet());
    }

    public Set<Tag> convertTagDTOsToTags(Set<TagDTO> tagDTOs) {
        if (tagDTOs == null) {
            return new HashSet<>();
        }
        return tagDTOs.stream()
                .map(this::convert)
                .collect(Collectors.toSet());
    }
}
