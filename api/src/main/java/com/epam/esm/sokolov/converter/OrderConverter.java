package com.epam.esm.sokolov.converter;

import com.epam.esm.sokolov.dto.OrderDTO;
import com.epam.esm.sokolov.dto.UserDTO;
import com.epam.esm.sokolov.model.Order;
import com.epam.esm.sokolov.model.user.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.stream.Collectors;

@NoArgsConstructor
@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class OrderConverter {

    private GiftCertificateConverter giftCertificateConverter;
    private UserConverter userConverter;

    public OrderDTO convert(Order source) {
        if (source == null) {
            return new OrderDTO();
        }
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
        if (source == null) {
            return new Order();
        }
        Order order = new Order();
        order.setId(source.getId());
        order.setCost(source.getCost());
        ZonedDateTime createDate = source.getCreateDate();
        if (createDate != null) {
            order.setCreateDate(DateConverter.getLocalDate(createDate));
            order.setCreateDateTimeZone(createDate.getZone().toString());
        }
        ZonedDateTime lastUpdateDate = source.getLastUpdateDate();
        if (lastUpdateDate != null) {
            order.setLastUpdateDate(DateConverter.getLocalDate(lastUpdateDate));
            order.setLastUpdateDateTimeZone(lastUpdateDate.getZone().toString());
        }
        UserDTO userDTO = source.getUserDTO();
        if (userDTO == null) {
            order.setUser(new User());
        } else {
            order.setUser(userConverter.convert(userDTO));
        }
        order.setGiftCertificates(source.getGiftCertificateDTOs()
                .stream()
                .map(giftCertificate -> giftCertificateConverter.convert(giftCertificate))
                .collect(Collectors.toSet())
        );
        return order;
    }
}
