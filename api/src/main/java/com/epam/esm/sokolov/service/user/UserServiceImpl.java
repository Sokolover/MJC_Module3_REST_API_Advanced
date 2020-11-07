package com.epam.esm.sokolov.service.user;

import com.epam.esm.sokolov.converter.OrderConverter;
import com.epam.esm.sokolov.converter.UserConverter;
import com.epam.esm.sokolov.dto.OrderDTO;
import com.epam.esm.sokolov.dto.UserDTO;
import com.epam.esm.sokolov.model.Order;
import com.epam.esm.sokolov.model.User;
import com.epam.esm.sokolov.repository.OrderRepository;
import com.epam.esm.sokolov.repository.UserRepository;
import com.epam.esm.sokolov.service.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserConverter userConverter;
    private OrderRepository orderRepository;
    private OrderConverter orderConverter;

    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter, OrderRepository orderRepository, OrderConverter orderConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.orderRepository = orderRepository;
        this.orderConverter = orderConverter;
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        User userToSave = userConverter.convert(userDTO);
        User saved = userRepository.save(userToSave);
        return userConverter.convert(saved);
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(user -> userConverter.convert(user))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> findAllOrdersByUserId(Long id) {
        return orderRepository.findAllByUserAccountId(id)
                .stream()
                .map(order -> orderConverter.convert(order))
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, String> findOneOrderByUserIdAndOrderId(Long userId, Long orderId) {
        Order order = orderRepository.findOneOrderByUserIdAndOrderId(userId, orderId)
                .orElse(new Order());
        if (order.getId() == null) {
            String message = String.format("Requested resource not found (id = %s)", orderId);
            throw new ServiceException(message, HttpStatus.NOT_FOUND, this.getClass());
        }
        OrderDTO orderDTO = orderConverter.convert(order);
        Map<String, String> orderParamMap = new HashMap<>();
        orderParamMap.put("cost", orderDTO.getCost().toString());
        orderParamMap.put("createDate", orderDTO.getCreateDate().toString());
        orderParamMap.put("lastUpdateDate", orderDTO.getLastUpdateDate().toString());
        return orderParamMap;
    }
}
