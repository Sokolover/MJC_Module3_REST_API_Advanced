package com.epam.esm.sokolov.service.certificate;

import com.epam.esm.sokolov.model.GiftCertificate;
import org.springframework.stereotype.Service;

@Service
public interface GiftCertificateMapper {

    GiftCertificate updateGiftCertificateFromDto(GiftCertificate giftCertificateFromDatabase, GiftCertificate giftCertificateFromController);
}
