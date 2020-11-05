package com.epam.esm.sokolov.service.order;

import com.epam.esm.sokolov.dto.OrderDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    OrderDTO save(OrderDTO orderDTO);

    List<OrderDTO> findAll();
}
