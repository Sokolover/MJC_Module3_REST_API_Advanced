package com.epam.esm.sokolov.service.certificate;

import com.epam.esm.sokolov.dto.GiftCertificateDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GiftCertificateService {

    GiftCertificateDTO update(Long id, GiftCertificateDTO dto);

    List<GiftCertificateDTO> findByTagNames(List<String> tagNames, Long size, Long page);

    Long findGiftCertificateAmountByTagNames(List<String> tagNames);
}
