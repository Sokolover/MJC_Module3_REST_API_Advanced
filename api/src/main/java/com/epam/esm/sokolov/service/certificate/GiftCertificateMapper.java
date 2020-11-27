package com.epam.esm.sokolov.service.certificate;

import com.epam.esm.sokolov.model.GiftCertificate;

public interface GiftCertificateMapper {

    void updateGiftCertificateFromDto(GiftCertificate giftCertificateFromDatabase, GiftCertificate giftCertificateFromController);
}
