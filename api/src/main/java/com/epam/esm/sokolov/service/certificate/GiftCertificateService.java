package com.epam.esm.sokolov.service.certificate;

import com.epam.esm.sokolov.dto.GiftCertificateDTO;

import java.util.List;

public interface GiftCertificateService {

    GiftCertificateDTO update(Long id, GiftCertificateDTO dto);

    List<GiftCertificateDTO> findByTagNames(List<String> tagNames, Long size, Long page);

    Long findGiftCertificateAmountByTagNames(List<String> tagNames);
}
