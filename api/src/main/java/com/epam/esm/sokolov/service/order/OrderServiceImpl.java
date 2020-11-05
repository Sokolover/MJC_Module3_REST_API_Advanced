package com.epam.esm.sokolov.service.order;

import com.epam.esm.sokolov.converter.OrderConverter;
import com.epam.esm.sokolov.dto.OrderDTO;
import com.epam.esm.sokolov.model.Order;
import com.epam.esm.sokolov.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private OrderConverter orderConverter;

    public OrderServiceImpl(OrderRepository orderRepository, OrderConverter orderConverter) {
        this.orderRepository = orderRepository;
        this.orderConverter = orderConverter;
    }

    @Override
    public OrderDTO save(OrderDTO orderDTO) {
        Order orderToSave = orderConverter.convert(orderDTO);
        if (orderToSave.getLastUpdateDate() == null) {
            orderToSave.setLastUpdateDate(orderToSave.getCreateDate());
            orderToSave.setLastUpdateDateTimeZone(orderToSave.getCreateDateTimeZone());
        }
        Order savedOrder = orderRepository.save(orderToSave);
        return orderConverter.convert(savedOrder);
    }

    @Override
    public List<OrderDTO> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(order -> orderConverter.convert(order))
                .collect(Collectors.toList());
    }
}
