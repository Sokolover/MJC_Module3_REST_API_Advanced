package com.epam.esm.sokolov.converter;

import com.epam.esm.sokolov.dto.GiftCertificateDTO;
import com.epam.esm.sokolov.dto.TagDTO;
import com.epam.esm.sokolov.exception.ConverterException;
import com.epam.esm.sokolov.model.GiftCertificate;
import com.epam.esm.sokolov.model.Tag;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.esm.sokolov.constants.CommonAppConstants.CONVERT_ERROR_MESSAGE;

@Service
@NoArgsConstructor
@AllArgsConstructor(onConstructor_ = @Autowired)
public class TagConverter {

    private ModelMapper modelMapper;

    public TagDTO convert(Tag source) {
        if (source == null) {
            throw new ConverterException(String.format(CONVERT_ERROR_MESSAGE, source.getClass().getSimpleName()));
        }
        return modelMapper.map(source, TagDTO.class);
    }

    public Tag convert(TagDTO source) {
        if (source == null) {
            throw new ConverterException(String.format(CONVERT_ERROR_MESSAGE, source.getClass().getSimpleName()));
        }
        return modelMapper.map(source, Tag.class);
    }

    public Set<TagDTO> convertTagsToTagDTOs(GiftCertificate source) {
        if (source.getTags() == null) {
            return new HashSet<>();
        }
        return source.getTags().stream()
                .map(this::convert)
                .collect(Collectors.toSet());
    }

    public Set<Tag> convertTagDTOsToTags(GiftCertificateDTO source) {
        if (source.getTags() == null) {
            return new HashSet<>();
        }
        return source.getTags().stream()
                .map(this::convert)
                .collect(Collectors.toSet());
    }
}
