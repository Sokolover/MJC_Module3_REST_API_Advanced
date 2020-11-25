package com.epam.esm.sokolov.repository.certificate;

import com.epam.esm.sokolov.model.GiftCertificate;

import java.util.List;
import java.util.Optional;

//fixme !!! check it: annotation @Component(@Service, @Repository) places only in class
public interface GiftCertificateRepository {

    List<GiftCertificate> findByTagsNames(List<String> tagNamesCondition, Long pageSize, Long pageOffsetInQuery);

    Optional<GiftCertificate> findById(Long id);

    GiftCertificate save(GiftCertificate giftCertificate);

    List<GiftCertificate> findAllById(List<Long> ids);

    Long findGiftCertificateAmountByTagNames(List<String> tagNamesCondition);
}
