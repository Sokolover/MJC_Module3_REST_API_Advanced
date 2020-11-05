package com.epam.esm.sokolov.service.certificate;

import com.epam.esm.sokolov.dto.GiftCertificateDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GiftCertificateService {

    GiftCertificateDTO update(GiftCertificateDTO dto);

    GiftCertificateDTO save(GiftCertificateDTO dto);

    List<GiftCertificateDTO> findAll();
}
