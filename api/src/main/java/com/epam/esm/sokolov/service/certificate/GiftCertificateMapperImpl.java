package com.epam.esm.sokolov.service.certificate;

import com.epam.esm.sokolov.model.GiftCertificate;
import com.epam.esm.sokolov.model.Tag;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Service
public class GiftCertificateMapperImpl implements GiftCertificateMapper {

    @Override
    public void updateGiftCertificateFromDto(GiftCertificate giftCertificateFromDatabase, GiftCertificate giftCertificateFromController) {
        String name = giftCertificateFromController.getName();
        if (name != null) {
            giftCertificateFromDatabase.setName(name);
        }
        String description = giftCertificateFromController.getDescription();
        if (description != null) {
            giftCertificateFromDatabase.setDescription(description);
        }
        BigDecimal price = giftCertificateFromController.getPrice();
        if (price != null) {
            giftCertificateFromDatabase.setPrice(price);
        }
        LocalDateTime createDate = giftCertificateFromController.getCreateDate();
        if (createDate != null) {
            giftCertificateFromDatabase.setCreateDate(createDate);
        }
        String createDateTimeZone = giftCertificateFromController.getCreateDateTimeZone();
        if (createDateTimeZone != null) {
            giftCertificateFromDatabase.setCreateDateTimeZone(createDateTimeZone);
        }
        LocalDateTime lastUpdateDate = giftCertificateFromController.getLastUpdateDate();
        if (lastUpdateDate != null) {
            giftCertificateFromDatabase.setLastUpdateDate(lastUpdateDate);
        }
        String lastUpdateDateTimeZone = giftCertificateFromController.getLastUpdateDateTimeZone();
        if (lastUpdateDateTimeZone != null) {
            giftCertificateFromDatabase.setLastUpdateDateTimeZone(lastUpdateDateTimeZone);
        }
        Integer duration = giftCertificateFromController.getDurationInDays();
        if (duration != null) {
            giftCertificateFromDatabase.setDurationInDays(duration);
        }
        Set<Tag> tags = giftCertificateFromController.getTags();
        if (tags != null) {
            giftCertificateFromDatabase.setTags(tags);
        }
    }
}
