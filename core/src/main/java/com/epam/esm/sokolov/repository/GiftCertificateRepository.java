package com.epam.esm.sokolov.repository;

import com.epam.esm.sokolov.model.GiftCertificate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GiftCertificateRepository {

    List<GiftCertificate> findAllByTagsName(List<String> tagNamesCondition);

    Optional<GiftCertificate> findById(Long id);

    GiftCertificate save(GiftCertificate giftCertificate);

    List<GiftCertificate> findAll();

    List<GiftCertificate> findAllById(List<Long> ids);
}
