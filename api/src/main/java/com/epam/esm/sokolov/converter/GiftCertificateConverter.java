package com.epam.esm.sokolov.converter;

import com.epam.esm.sokolov.dto.GiftCertificateDTO;
import com.epam.esm.sokolov.dto.OrderDTO;
import com.epam.esm.sokolov.dto.TagDTO;
import com.epam.esm.sokolov.exception.ConverterException;
import com.epam.esm.sokolov.model.GiftCertificate;
import com.epam.esm.sokolov.model.Order;
import com.epam.esm.sokolov.model.Tag;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.esm.sokolov.constants.CommonAppConstants.CONVERT_ERROR_MESSAGE;
import static org.springframework.util.CollectionUtils.isEmpty;

@Service
@NoArgsConstructor
@AllArgsConstructor(onConstructor_ = @Autowired)
public class GiftCertificateConverter {

    private TagConverter tagConverter;
    private ModelMapper modelMapper;

    public GiftCertificateDTO convert(GiftCertificate source) {
        if (source == null) {
            throw new ConverterException(String.format(CONVERT_ERROR_MESSAGE, source.getClass().getSimpleName()));
        }

        GiftCertificateDTO result = modelMapper.map(source, GiftCertificateDTO.class);

        LocalDateTime createDate = source.getCreateDate();
        String createDateTimeZone = source.getCreateDateTimeZone();
        if (createDate != null && createDateTimeZone != null) {
            result.setCreateDate(DateConverter.getZonedDateTime(createDate, createDateTimeZone));
        }

        LocalDateTime lastUpdateDate = source.getLastUpdateDate();
        String lastUpdateDateTimeZone = source.getLastUpdateDateTimeZone();
        if (lastUpdateDate != null && lastUpdateDateTimeZone != null) {
            result.setLastUpdateDate(DateConverter.getZonedDateTime(lastUpdateDate, lastUpdateDateTimeZone));
        }

        Set<Tag> tags = source.getTags();
        if (!isEmpty(tags)) {
            result.setTags(tagConverter.convertTagsToTagDTOs(source));
        }
        return result;
    }

    public GiftCertificate convert(GiftCertificateDTO source) {
        if (source == null) {
            throw new ConverterException(String.format(CONVERT_ERROR_MESSAGE, source.getClass().getSimpleName()));
        }

        GiftCertificate result = modelMapper.map(source, GiftCertificate.class);

        ZonedDateTime createDate = source.getCreateDate();
        if (createDate != null) {
            result.setCreateDate(DateConverter.getLocalDate(createDate));
            result.setCreateDateTimeZone(createDate.getZone().toString());
        }

        ZonedDateTime lastUpdateDate = source.getLastUpdateDate();
        if (lastUpdateDate != null) {
            result.setLastUpdateDate(DateConverter.getLocalDate(lastUpdateDate));
            result.setLastUpdateDateTimeZone(lastUpdateDate.getZone().toString());
        }

        Set<TagDTO> tags = source.getTags();
        if (!isEmpty(tags)) {
            result.setTags(tagConverter.convertTagDTOsToTags(source));
        }
        return result;
    }

    public Set<GiftCertificateDTO> convertGiftCertificatesToGiftCertificateDTOs(Order source) {
        if (source.getGiftCertificates() == null) {
            return new HashSet<>();
        }
        return source.getGiftCertificates().stream()
                .map(this::convert)
                .collect(Collectors.toSet());
    }

    public Set<GiftCertificate> convertGiftCertificateDTOsToGiftCertificates(OrderDTO source) {
        if (source.getGiftCertificateDTOs() == null) {
            return new HashSet<>();
        }
        return source.getGiftCertificateDTOs().stream()
                .map(this::convert)
                .collect(Collectors.toSet());
    }
}
