package com.epam.esm.sokolov.converter;

import com.epam.esm.sokolov.dto.GiftCertificateDTO;
import com.epam.esm.sokolov.dto.TagDTO;
import com.epam.esm.sokolov.model.GiftCertificate;
import com.epam.esm.sokolov.model.Tag;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;

@Service
@NoArgsConstructor
public class GiftCertificateConverter {

    private TagConverter tagConverter;
    private ModelMapper modelMapper;
    private ModelMapper mapperGiftCertificateDTOToGiftCertificate;

    @Autowired
    public GiftCertificateConverter(TagConverter tagConverter,
                                    ModelMapper modelMapper,
                                    @Qualifier("mapperGiftCertificateDTOToGiftCertificate") ModelMapper mapperGiftCertificateDTOToGiftCertificate) {
        this.tagConverter = tagConverter;
        this.modelMapper = modelMapper;
        this.mapperGiftCertificateDTOToGiftCertificate = mapperGiftCertificateDTOToGiftCertificate;
    }

    public GiftCertificateDTO convert(GiftCertificate source) {
        if (source == null) {
            return new GiftCertificateDTO();
        }

        GiftCertificateDTO result = modelMapper.map(source, GiftCertificateDTO.class);

        LocalDateTime createDate = source.getCreateDate();
        String createDateTimeZone = source.getCreateDateTimeZone();
        if (createDate != null && createDateTimeZone != null) {
            result.setCreateDate(DateConverter.getZonedDateTime(createDate, createDateTimeZone));
        }

        LocalDateTime lastUpdateDate = source.getLastUpdateDate();
        String lastUpdateDateTimeZone = source.getLastUpdateDateTimeZone();
        if (lastUpdateDate != null && lastUpdateDateTimeZone != null) {
            result.setLastUpdateDate(DateConverter.getZonedDateTime(lastUpdateDate, lastUpdateDateTimeZone));
        }

        Set<Tag> sourceTags = source.getTags();
        if (!isEmpty(sourceTags)) {
            Set<TagDTO> resultTags = tagConverter.convertTagsToTagDTOs(sourceTags);
            result.setTags(resultTags);
        }
        return result;
    }

    public GiftCertificate convert(GiftCertificateDTO source) {
        if (source == null) {
            return new GiftCertificate();
        }

        GiftCertificate result = mapperGiftCertificateDTOToGiftCertificate.map(source, GiftCertificate.class);

        Set<TagDTO> sourceTags = source.getTags();
        if (!isEmpty(sourceTags)) {
            Set<Tag> resultTags = tagConverter.convertTagDTOsToTags(sourceTags);
            result.setTags(resultTags);
        }
        return result;
    }

    public Set<GiftCertificateDTO> convertGiftCertificatesToGiftCertificateDTOs(Set<GiftCertificate> source) {
        if (source == null) {
            return new HashSet<>();
        }
        return source.stream()
                .map(this::convert)
                .collect(Collectors.toSet());
    }

    public Set<GiftCertificate> convertGiftCertificateDTOsToGiftCertificates(Set<GiftCertificateDTO> source) {
        if (source == null) {
            return new HashSet<>();
        }
        return source.stream()
                .map(this::convert)
                .collect(Collectors.toSet());
    }
}
