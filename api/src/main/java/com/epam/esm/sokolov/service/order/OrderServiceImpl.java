package com.epam.esm.sokolov.service.order;

import com.epam.esm.sokolov.converter.OrderConverter;
import com.epam.esm.sokolov.dto.OrderDTO;
import com.epam.esm.sokolov.model.GiftCertificate;
import com.epam.esm.sokolov.model.Order;
import com.epam.esm.sokolov.repository.GiftCertificateRepository;
import com.epam.esm.sokolov.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private OrderConverter orderConverter;
    private GiftCertificateRepository giftCertificateRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderConverter orderConverter, GiftCertificateRepository giftCertificateRepository) {
        this.orderRepository = orderRepository;
        this.orderConverter = orderConverter;
        this.giftCertificateRepository = giftCertificateRepository;
    }

    @Override
    public OrderDTO save(OrderDTO orderDTO) {
        Order orderToSave = orderConverter.convert(orderDTO);
        setOrderCost(orderToSave);
        setCurrentTimeToOrder(orderToSave);
        Order savedOrder = orderRepository.save(orderToSave);
        return orderConverter.convert(savedOrder);
    }

    private void setCurrentTimeToOrder(Order orderToSave) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        String currentTimeZone = ZonedDateTime.now().getOffset().toString();
        orderToSave.setCreateDate(currentDateTime);
        orderToSave.setCreateDateTimeZone(currentTimeZone);
        orderToSave.setLastUpdateDate(currentDateTime);
        orderToSave.setLastUpdateDateTimeZone(currentTimeZone);
    }

    private void setOrderCost(Order orderToSave) {
        List<Long> getGiftCertificateIds = orderToSave.getGiftCertificates()
                .stream()
                .map(GiftCertificate::getId)
                .collect(Collectors.toList());
        BigDecimal orderCost = giftCertificateRepository.findAllById(getGiftCertificateIds)
                .stream()
                .map(GiftCertificate::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        orderToSave.setCost(orderCost);
    }

    @Override
    public List<OrderDTO> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(order -> orderConverter.convert(order))
                .collect(Collectors.toList());
    }
}
