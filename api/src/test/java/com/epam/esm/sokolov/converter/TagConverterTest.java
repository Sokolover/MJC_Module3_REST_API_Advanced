package com.epam.esm.sokolov.converter;

import com.epam.esm.sokolov.dto.TagDTO;
import com.epam.esm.sokolov.model.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Sokolov_SA
 * @created 26.10.2021
 */
@ExtendWith(MockitoExtension.class)
class TagConverterTest {

    @InjectMocks
    private final TagConverter tagConverter = new TagConverter();
    @Spy
    private ModelMapper modelMapper;

    @Test
    void convertTagToTagDTO() {
        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("tag1");

        TagDTO result = tagConverter.convert(tag);
        assertEquals(tag.getId(), result.getId());
        assertEquals(tag.getName(), result.getName());
    }

    @Test
    void convertTagDTOToTag() {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setId(1L);
        tagDTO.setName("tagDTO1");

        Tag result = tagConverter.convert(tagDTO);
        assertEquals(tagDTO.getId(), result.getId());
        assertEquals(tagDTO.getName(), result.getName());
    }
}