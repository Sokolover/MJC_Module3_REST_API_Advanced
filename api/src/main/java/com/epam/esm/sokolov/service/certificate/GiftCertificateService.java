package com.epam.esm.sokolov.service.certificate;

import com.epam.esm.sokolov.dto.GiftCertificateDTO;
import com.epam.esm.sokolov.model.GiftCertificate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GiftCertificateService {

    GiftCertificateDTO update(GiftCertificateDTO dto);

    GiftCertificate save(GiftCertificateDTO dto);

    List<GiftCertificate> findAll();
}
