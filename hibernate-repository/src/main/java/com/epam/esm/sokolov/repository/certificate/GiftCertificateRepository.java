package com.epam.esm.sokolov.repository.certificate;

import com.epam.esm.sokolov.model.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateRepository {

    List<GiftCertificate> findByTagNames(List<String> tagNamesCondition, Long pageSize, Long pageOffsetInQuery);

    Optional<GiftCertificate> findById(Long id);

    GiftCertificate save(GiftCertificate giftCertificate);

    List<GiftCertificate> findAllByIdIn(List<Long> ids);

    Long countGiftCertificatesByTagsNameIn(List<String> tagNamesCondition);
}
