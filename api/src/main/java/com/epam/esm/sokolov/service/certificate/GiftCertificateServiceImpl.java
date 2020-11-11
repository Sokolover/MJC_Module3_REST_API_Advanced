package com.epam.esm.sokolov.service.certificate;

import com.epam.esm.sokolov.converter.GiftCertificateConverter;
import com.epam.esm.sokolov.dto.GiftCertificateDTO;
import com.epam.esm.sokolov.exception.ServiceException;
import com.epam.esm.sokolov.model.GiftCertificate;
import com.epam.esm.sokolov.repository.certificate.GiftCertificateRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.sokolov.constants.CommonAppConstants.INCORRECT_PAGE_SIZE_MESSAGE;
import static com.epam.esm.sokolov.constants.CommonAppConstants.INCORRECT_TAG_NAMES_MESSAGE;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private GiftCertificateRepository giftCertificateRepository;
    private GiftCertificateConverter giftCertificateConverter;
    private GiftCertificateMapper giftCertificateMapper;

    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepository, GiftCertificateConverter giftCertificateConverter, GiftCertificateMapper giftCertificateMapper) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.giftCertificateConverter = giftCertificateConverter;
        this.giftCertificateMapper = giftCertificateMapper;
    }

    @Override
    @Transactional
    public GiftCertificateDTO update(Long id, GiftCertificateDTO dto) {
        GiftCertificate giftCertificateFromDatabase = giftCertificateRepository.findById(id)
                .<ServiceException>orElseThrow(() -> {
                    String message = String.format("Resource not found (id = %s)", id);
                    throw new ServiceException(message, HttpStatus.NOT_FOUND, this.getClass());
                });
        GiftCertificate giftCertificateFromController = giftCertificateConverter.convert(dto);
        giftCertificateMapper.updateGiftCertificateFromDto(giftCertificateFromDatabase, giftCertificateFromController);
        GiftCertificate savedGiftCertificate = giftCertificateRepository.save(giftCertificateFromDatabase);
        return giftCertificateConverter.convert(savedGiftCertificate);
    }

    @Override
    public List<GiftCertificateDTO> findByTagNames(List<String> tagNames, Long pageSize, Long pageNumber) {
        if (isIncorrectArguments(tagNames, pageSize, pageNumber)) {
            String message = String.format("%s, %s", INCORRECT_PAGE_SIZE_MESSAGE, INCORRECT_TAG_NAMES_MESSAGE);
            throw new ServiceException(message, HttpStatus.BAD_REQUEST, this.getClass());
        }
        Long pageOffsetInQuery = pageNumber * pageSize;
        List<GiftCertificate> giftCertificates = giftCertificateRepository.findByTagsNames(tagNames, pageSize, pageOffsetInQuery);
        if (CollectionUtils.isEmpty(giftCertificates)) {
            String message = String.format("%s, %s", INCORRECT_PAGE_SIZE_MESSAGE, INCORRECT_TAG_NAMES_MESSAGE);
            throw new ServiceException(message, HttpStatus.BAD_REQUEST, this.getClass());
        }
        return giftCertificates.stream()
                .map(giftCertificateConverter::convert)
                .collect(Collectors.toList());
    }

    private boolean isIncorrectArguments(List<String> tagNames, Long pageSize, Long pageNumber) {
        return tagNames == null || pageSize == null || pageNumber == null || pageSize < 0 || pageNumber < 0;
    }

    @Override
    public Long findGiftCertificateAmountByTagNames(List<String> tagNames) {
        return giftCertificateRepository.findGiftCertificateAmountByTagNames(tagNames);
    }
}
