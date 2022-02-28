package com.epam.esm.sokolov.repository.order;

import com.epam.esm.sokolov.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    List<Order> findAllByUserId(Long id, Long size, Long page);

    Optional<Order> findOrderByUserIdAndId(Long userId, Long orderId);

    Order save(Order order);

    Long countOrderByUserId(Long id);
}
