package com.epam.esm.sokolov.service.certificate;

import com.epam.esm.sokolov.converter.GiftCertificateConverter;
import com.epam.esm.sokolov.dto.GiftCertificateDTO;
import com.epam.esm.sokolov.exception.ServiceException;
import com.epam.esm.sokolov.model.GiftCertificate;
import com.epam.esm.sokolov.repository.certificate.GiftCertificateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.sokolov.constants.CommonAppConstants.INCORRECT_PAGE_SIZE_MESSAGE;
import static com.epam.esm.sokolov.constants.CommonAppConstants.INCORRECT_TAG_NAMES_MESSAGE;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateRepository giftCertificateRepository;
    private final GiftCertificateConverter giftCertificateConverter;
    private final GiftCertificateMapper giftCertificateMapper;

    @Override
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
        List<GiftCertificate> giftCertificates = giftCertificateRepository.findByTagNames(tagNames, pageSize, pageOffsetInQuery);
        return giftCertificates.stream()
                .map(giftCertificateConverter::convert)
                .collect(Collectors.toList());
    }

    private boolean isIncorrectArguments(List<String> tagNames, Long pageSize, Long pageNumber) {
        return tagNames == null || pageSize == null || pageNumber == null || pageSize <= 0 || pageNumber < 0;
    }

    @Override
    public Long findGiftCertificateAmountByTagNames(List<String> tagNames) {
        return giftCertificateRepository.countGiftCertificatesByTagsNameIn(tagNames);
    }
}
