package com.epam.esm.sokolov.converter;

import com.epam.esm.sokolov.dto.OrderDTO;
import com.epam.esm.sokolov.model.Order;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
public class OrderConverter {

    private GiftCertificateConverter giftCertificateConverter;
    private UserConverter userConverter;

    public OrderConverter(GiftCertificateConverter giftCertificateConverter, UserConverter userConverter) {
        this.giftCertificateConverter = giftCertificateConverter;
        this.userConverter = userConverter;
    }

    public OrderDTO convert(Order source) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(source.getId());
        orderDTO.setCost(source.getCost());
        orderDTO.setCreateDate(DateConverter.getZonedDateTime(source.getCreateDate(), source.getCreateDateTimeZone()));
        orderDTO.setLastUpdateDate(DateConverter.getZonedDateTime(source.getLastUpdateDate(), source.getLastUpdateDateTimeZone()));
        orderDTO.setUserDTO(userConverter.convert(source.getUser()));
        orderDTO.setGiftCertificateDTOs(source.getGiftCertificates()
                .stream()
                .map(giftCertificate -> giftCertificateConverter.convert(giftCertificate))
                .collect(Collectors.toSet())
        );
        return orderDTO;
    }

    public Order convert(OrderDTO source) {
        Order order = new Order();
        order.setId(source.getId());
        order.setCost(source.getCost());
        ZonedDateTime createDate = source.getCreateDate();
        if (nonNull(createDate)) {
            order.setCreateDate(DateConverter.getLocalDate(createDate));
            order.setCreateDateTimeZone(createDate.getZone().toString());
        }
        ZonedDateTime lastUpdateDate = source.getLastUpdateDate();
        if (nonNull(lastUpdateDate)) {
            order.setLastUpdateDate(DateConverter.getLocalDate(lastUpdateDate));
            order.setLastUpdateDateTimeZone(lastUpdateDate.getZone().toString());
        }
        order.setUser(userConverter.convert(source.getUserDTO()));
        order.setGiftCertificates(source.getGiftCertificateDTOs()
                .stream()
                .map(giftCertificate -> giftCertificateConverter.convert(giftCertificate))
                .collect(Collectors.toSet())
        );
        return order;
    }
}
