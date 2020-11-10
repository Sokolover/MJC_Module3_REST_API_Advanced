package com.epam.esm.sokolov.service.certificate;

import com.epam.esm.sokolov.converter.GiftCertificateConverter;
import com.epam.esm.sokolov.dto.GiftCertificateDTO;
import com.epam.esm.sokolov.exception.ServiceException;
import com.epam.esm.sokolov.model.GiftCertificate;
import com.epam.esm.sokolov.repository.GiftCertificateRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateRepository giftCertificateRepository;
    private final GiftCertificateConverter giftCertificateConverter;
    //    @Autowired
//    private GiftCertificateMapper  giftCertificateMapper;

    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepository, GiftCertificateConverter giftCertificateConverter) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.giftCertificateConverter = giftCertificateConverter;
    }

    public GiftCertificateDTO update(GiftCertificateDTO dto) {
        GiftCertificate giftCertificateFromDatabase = giftCertificateRepository.findById(dto.getId())
                .<ServiceException>orElseThrow(() -> {
                    String message = String.format("Requested resource not found (id = %s)", dto.getId());
                    throw new ServiceException(message, HttpStatus.NOT_FOUND, this.getClass());
                });
        GiftCertificate giftCertificateFromController = giftCertificateConverter.convert(dto);
//        giftCertificateFromDatabase = giftCertificateMapper.updateGiftCertificateFromDto(giftCertificateFromController);
        GiftCertificate savedGiftCertificate = giftCertificateRepository.save(giftCertificateFromDatabase);
        return giftCertificateConverter.convert(savedGiftCertificate);
    }

    @Override
    public GiftCertificateDTO save(GiftCertificateDTO dto) {
        GiftCertificate giftCertificateToSave = giftCertificateConverter.convert(dto);
        GiftCertificate savedGiftCertificate = giftCertificateRepository.save(giftCertificateToSave);
        return giftCertificateConverter.convert(savedGiftCertificate);
    }

    @Override
    public List<GiftCertificateDTO> findAll() {
        return giftCertificateRepository.findAll().stream()
                .map(giftCertificateConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public List<GiftCertificateDTO> findAllByTagNames(List<String> tagNames) {
        List<GiftCertificate> giftCertificates = giftCertificateRepository.findAllByTagsName(tagNames);
        return giftCertificates.stream()
                .map(giftCertificateConverter::convert)
                .collect(Collectors.toList());
    }
}
