package com.epam.esm.sokolov.converter;

import com.epam.esm.sokolov.dto.GiftCertificateDTO;
import com.epam.esm.sokolov.dto.TagDTO;
import com.epam.esm.sokolov.model.GiftCertificate;
import com.epam.esm.sokolov.model.Tag;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Set;

@NoArgsConstructor
@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class GiftCertificateConverter {

    private TagConverter tagConverter;

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
        giftCertificateDTO.setDurationInDays(source.getDurationInDays());
        Set<Tag> tags = source.getTags();
        if (tags != null) {
            giftCertificateDTO.setTags(tagConverter.convertTagDtosFromTags(tags));
        }
        return giftCertificateDTO;
    }

    public GiftCertificate convert(GiftCertificateDTO source) {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(source.getId());
        giftCertificate.setName(source.getName());
        ZonedDateTime createDate = source.getCreateDate();
        if (createDate != null) {
            giftCertificate.setCreateDate(DateConverter.getLocalDate(createDate));
            giftCertificate.setCreateDateTimeZone(createDate.getZone().toString());
        }
        ZonedDateTime lastUpdateDate = source.getLastUpdateDate();
        if (lastUpdateDate != null) {
            giftCertificate.setLastUpdateDate(DateConverter.getLocalDate(lastUpdateDate));
            giftCertificate.setLastUpdateDateTimeZone(lastUpdateDate.getZone().toString());
        }
        giftCertificate.setDescription(source.getDescription());
        giftCertificate.setPrice(source.getPrice());
        giftCertificate.setDurationInDays(source.getDurationInDays());
        Set<TagDTO> tags = source.getTags();
        if (tags != null) {
            giftCertificate.setTags(tagConverter.convertTagsFromTagDtos(tags));
        }
        return giftCertificate;
    }
}
