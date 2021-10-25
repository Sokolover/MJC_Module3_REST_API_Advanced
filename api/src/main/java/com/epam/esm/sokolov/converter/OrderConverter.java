package com.epam.esm.sokolov.converter;

import com.epam.esm.sokolov.dto.GiftCertificateDTO;
import com.epam.esm.sokolov.dto.OrderDTO;
import com.epam.esm.sokolov.dto.UserDTO;
import com.epam.esm.sokolov.exception.ConverterException;
import com.epam.esm.sokolov.model.GiftCertificate;
import com.epam.esm.sokolov.model.Order;
import com.epam.esm.sokolov.model.user.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Set;

import static com.epam.esm.sokolov.constants.CommonAppConstants.CONVERT_ERROR_MESSAGE;
import static org.springframework.util.CollectionUtils.isEmpty;

@Service
@NoArgsConstructor
@AllArgsConstructor(onConstructor_ = @Autowired)
public class OrderConverter {

    private GiftCertificateConverter giftCertificateConverter;
    private UserConverter userConverter;
    private ModelMapper modelMapper;

    public OrderDTO convert(Order source) {
        if (source == null) {
            throw new ConverterException(String.format(CONVERT_ERROR_MESSAGE, source.getClass().getSimpleName()));
        }

        OrderDTO result = modelMapper.map(source, OrderDTO.class);

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

        User user = source.getUser();
        if (user != null) {
            result.setUserDTO(userConverter.convert(user));
        }
        if (!isEmpty(source.getGiftCertificates())) {
            Set<GiftCertificateDTO> giftCertificateDTOS = giftCertificateConverter.convertGiftCertificatesToGiftCertificateDTOs(source);
            result.setGiftCertificateDTOs(giftCertificateDTOS);
        }

        return result;
    }

    public Order convert(OrderDTO source) {
        if (source == null) {
            throw new ConverterException(String.format(CONVERT_ERROR_MESSAGE, source.getClass().getSimpleName()));
        }

        Order result = modelMapper.map(source, Order.class);

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

        UserDTO userDTO = source.getUserDTO();
        result.setUser(userConverter.convert(userDTO));
        if (!isEmpty(source.getGiftCertificateDTOs())) {
            Set<GiftCertificate> giftCertificates = giftCertificateConverter.convertGiftCertificateDTOsToGiftCertificates(source);
            result.setGiftCertificates(giftCertificates);
        }

        return result;
    }
}
