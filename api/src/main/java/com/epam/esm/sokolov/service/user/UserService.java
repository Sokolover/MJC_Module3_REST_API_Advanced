package com.epam.esm.sokolov.service.user;

import com.epam.esm.sokolov.dto.OrderDTO;
import com.epam.esm.sokolov.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface UserService {

    UserDTO save(UserDTO user);

    List<UserDTO> findAll();

    List<OrderDTO> findAllOrdersByUserId(Long id, Long size, Long page);

    Map<String, String> findOneOrderByUserIdAndOrderId(Long userId, Long orderId);

    Long findOrderAmountByUserId(Long id);
}
