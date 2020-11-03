package com.epam.esm.sokolov.repository;

import com.epam.esm.sokolov.model.GiftCertificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long> {
}
