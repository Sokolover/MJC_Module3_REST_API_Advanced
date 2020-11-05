package com.epam.esm.sokolov.service.certificate;

import com.epam.esm.sokolov.converter.GiftCertificateConverter;
import com.epam.esm.sokolov.dto.GiftCertificateDTO;
import com.epam.esm.sokolov.model.GiftCertificate;
import com.epam.esm.sokolov.repository.GiftCertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    @Autowired
    private GiftCertificateRepository giftCertificateRepository;
    //    @Autowired
//    private GiftCertificateMapper  giftCertificateMapper;
    @Autowired
    private GiftCertificateConverter giftCertificateConverter;

    public GiftCertificateDTO update(GiftCertificateDTO dto) {
        GiftCertificate giftCertificateFromDatabase = giftCertificateRepository.findById(dto.getId())
                .orElse(new GiftCertificate());
//                .orElseThrow(() -> {
//                    String message = String.format("Requested resource not found (id = %s)", dto.getId());
//                    throw new RepositoryException(message, HttpStatus.NOT_FOUND, this.getClass());
//                });
        GiftCertificate giftCertificateFromController = giftCertificateConverter.convert(dto);
//        giftCertificateFromDatabase = giftCertificateMapper.updateGiftCertificateFromDto(giftCertificateFromController);
        GiftCertificate savedGiftCertificate = giftCertificateRepository.save(giftCertificateFromDatabase);
        return giftCertificateConverter.convert(savedGiftCertificate);
    }

    @Override
    public GiftCertificate save(GiftCertificateDTO dto) {
        return null;
    }

    @Override
    public List<GiftCertificate> findAll() {
        return giftCertificateRepository.findAll();
    }

}
