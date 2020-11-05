package com.epam.esm.sokolov.converter;

import com.epam.esm.sokolov.dto.GiftCertificateDTO;
import com.epam.esm.sokolov.dto.TagDTO;
import com.epam.esm.sokolov.model.GiftCertificate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Set;

import static com.epam.esm.sokolov.converter.TagConverter.convertTagsDtosFromTag;
import static com.epam.esm.sokolov.converter.TagConverter.convertTagsFromTagDtos;
import static java.util.Objects.nonNull;

@Service
public class GiftCertificateConverter {

    public GiftCertificateDTO convert(GiftCertificate source) {
        GiftCertificateDTO giftCertificateDTO = new GiftCertificateDTO();
        giftCertificateDTO.setId(source.getId());
        giftCertificateDTO.setName(source.getName());
        LocalDateTime createDate = source.getCreateDate();
        String createDateTimeZone = source.getCreateDateTimeZone();
        if (createDate != null && createDateTimeZone != null) {
            giftCertificateDTO.setCreateDate(DateConverter.getZonedDateTime(createDate, createDateTimeZone));
        }
        LocalDateTime lastUpdateDate = source.getLastUpdateDate();
        String lastUpdateDateTimeZone = source.getLastUpdateDateTimeZone();
        if (lastUpdateDate != null && lastUpdateDateTimeZone != null) {
            giftCertificateDTO.setLastUpdateDate(DateConverter.getZonedDateTime(lastUpdateDate, lastUpdateDateTimeZone));
        }
        giftCertificateDTO.setDescription(source.getDescription());
        giftCertificateDTO.setPrice(source.getPrice());
        giftCertificateDTO.setDuration(source.getDuration());
        giftCertificateDTO.setTags(convertTagsDtosFromTag(source.getTags()));
        return giftCertificateDTO;
    }

    public GiftCertificate convert(GiftCertificateDTO source) {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(source.getId());
        giftCertificate.setName(source.getName());
        ZonedDateTime createDate = source.getCreateDate();
        if (nonNull(createDate)) {
            giftCertificate.setCreateDate(DateConverter.getLocalDate(createDate));
            giftCertificate.setCreateDateTimeZone(createDate.getZone().toString());
        }
        ZonedDateTime lastUpdateDate = source.getLastUpdateDate();
        if (nonNull(lastUpdateDate)) {
            giftCertificate.setLastUpdateDate(DateConverter.getLocalDate(lastUpdateDate));
            giftCertificate.setLastUpdateDateTimeZone(lastUpdateDate.getZone().toString());
        }
        giftCertificate.setDescription(source.getDescription());
        giftCertificate.setPrice(source.getPrice());
        giftCertificate.setDuration(source.getDuration());
        Set<TagDTO> tags = source.getTags();
        if (nonNull(tags)) {
            giftCertificate.setTags(convertTagsFromTagDtos(tags));
        }
        return giftCertificate;

    }
}
