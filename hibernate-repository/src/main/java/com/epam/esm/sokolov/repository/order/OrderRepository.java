package com.epam.esm.sokolov.repository.order;

import com.epam.esm.sokolov.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    List<Order> findAllByUserAccountId(Long id, Long size, Long page);

    Optional<Order> findOneOrderByUserIdAndOrderId(Long userId, Long orderId);

    Order save(Order order);

    Long findOrderAmountByUserId(Long id);
}
