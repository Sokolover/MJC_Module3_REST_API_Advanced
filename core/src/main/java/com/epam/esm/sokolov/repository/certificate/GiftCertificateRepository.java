package com.epam.esm.sokolov.repository.certificate;

import com.epam.esm.sokolov.model.GiftCertificate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GiftCertificateRepository {

    List<GiftCertificate> findByTagsNames(List<String> tagNamesCondition, Long pageSize, Long pageOffsetInQuery);

    Optional<GiftCertificate> findById(Long id);

    GiftCertificate save(GiftCertificate giftCertificate);

    List<GiftCertificate> findAllById(List<Long> ids);

    Long findGiftCertificateAmountByTagNames(List<String> tagNamesCondition);
}
