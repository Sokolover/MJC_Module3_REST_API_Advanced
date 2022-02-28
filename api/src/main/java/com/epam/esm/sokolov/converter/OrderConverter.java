package com.epam.esm.sokolov.converter;

import com.epam.esm.sokolov.dto.GiftCertificateDTO;
import com.epam.esm.sokolov.dto.OrderDTO;
import com.epam.esm.sokolov.dto.UserInDTO;
import com.epam.esm.sokolov.dto.UserOutDTO;
import com.epam.esm.sokolov.model.GiftCertificate;
import com.epam.esm.sokolov.model.Order;
import com.epam.esm.sokolov.model.user.User;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

import static org.springframework.util.CollectionUtils.isEmpty;

@Service
@NoArgsConstructor
public class OrderConverter {

    private GiftCertificateConverter giftCertificateConverter;
    private UserConverter userConverter;
    private ModelMapper modelMapper;
    private ModelMapper mapperOrderToOrderDTO;

    @Autowired
    public OrderConverter(GiftCertificateConverter giftCertificateConverter,
                          UserConverter userConverter,
                          ModelMapper modelMapper,
                          @Qualifier("mapperOrderToOrderDTO") ModelMapper mapperOrderToOrderDTO) {
        this.giftCertificateConverter = giftCertificateConverter;
        this.userConverter = userConverter;
        this.modelMapper = modelMapper;
        this.mapperOrderToOrderDTO = mapperOrderToOrderDTO;
    }

    public OrderDTO convert(Order source) {
        if (source == null) {
            return new OrderDTO();
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
            UserOutDTO userOutDTO = userConverter.convert(user);
            result.setUserOutDTO(userOutDTO);
        }

        Set<GiftCertificate> giftCertificates = source.getGiftCertificates();
        if (!isEmpty(giftCertificates)) {
            Set<GiftCertificateDTO> giftCertificateDTOS = giftCertificateConverter
                    .convertGiftCertificatesToGiftCertificateDTOs(giftCertificates);
            result.setGiftCertificateDTOs(giftCertificateDTOS);
        }

        return result;
    }

    public Order convert(OrderDTO source) {
        if (source == null) {
            return new Order();
        }

        Order result = mapperOrderToOrderDTO.map(source, Order.class);

        UserInDTO userInDTO = source.getUserInDTO();
        if (userInDTO != null) {
            User user = userConverter.convert(userInDTO);
            result.setUser(user);
        }

        Set<GiftCertificateDTO> giftCertificateDTOs = source.getGiftCertificateDTOs();
        if (!isEmpty(giftCertificateDTOs)) {
            Set<GiftCertificate> giftCertificates = giftCertificateConverter
                    .convertGiftCertificateDTOsToGiftCertificates(giftCertificateDTOs);
            result.setGiftCertificates(giftCertificates);
        }

        return result;
    }
}
