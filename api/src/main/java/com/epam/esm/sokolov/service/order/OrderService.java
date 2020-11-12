package com.epam.esm.sokolov.service.order;

import com.epam.esm.sokolov.dto.OrderDTO;
import com.epam.esm.sokolov.dto.OrderDetailsDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    OrderDTO save(OrderDTO orderDTO);

    List<OrderDTO> findAllOrdersByUserId(Long id, Long size, Long page);

    OrderDetailsDTO findOneOrderByUserIdAndOrderId(Long userId, Long orderId);

    Long findOrderAmountByUserId(Long id);
}
