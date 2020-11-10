package com.epam.esm.sokolov.service.user;

import com.epam.esm.sokolov.converter.OrderConverter;
import com.epam.esm.sokolov.converter.UserConverter;
import com.epam.esm.sokolov.dto.OrderDTO;
import com.epam.esm.sokolov.dto.UserDTO;
import com.epam.esm.sokolov.exception.ServiceException;
import com.epam.esm.sokolov.model.Order;
import com.epam.esm.sokolov.model.User;
import com.epam.esm.sokolov.repository.order.OrderRepository;
import com.epam.esm.sokolov.repository.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final String COST = "cost";
    private static final String CREATE_DATE = "createDate";
    private static final String LAST_UPDATE_DATE = "lastUpdateDate";

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
    @Transactional
    public List<OrderDTO> findAllOrdersByUserId(Long id, Long pageSize, Long pageNumber) {
        if (pageSize == null || pageNumber == null) {//todo this exception handle as 500 error in controller tier
            throw new ServiceException("Size or page wasn't set in URI correctly", HttpStatus.BAD_REQUEST, this.getClass());
        }
        Long pageOffsetInQuery = pageNumber * pageSize;
        return orderRepository.findAllByUserAccountId(id, pageSize, pageOffsetInQuery)
                .stream()
                .map(order -> orderConverter.convert(order))
                .collect(Collectors.toList());
    }

    @Override
    public Long findOrderAmountByUserId(Long id) {
        return orderRepository.findOrderAmountByUserId(id);
    }

    @Override
    public Map<String, String> findOneOrderByUserIdAndOrderId(Long userId, Long orderId) {
        Order order = orderRepository.findOneOrderByUserIdAndOrderId(userId, orderId)
                .<ServiceException>orElseThrow(() -> {
                    String message = String.format("Resource not found (order id = %s)", orderId);
                    throw new ServiceException(message, HttpStatus.NOT_FOUND, this.getClass());
                });
        OrderDTO orderDTO = orderConverter.convert(order);
        return populateOrderParamMap(orderDTO);
    }

    private Map<String, String> populateOrderParamMap(OrderDTO orderDTO) {
        Map<String, String> orderParamMap = new HashMap<>();
        orderParamMap.put(COST, orderDTO.getCost().toString());
        orderParamMap.put(CREATE_DATE, orderDTO.getCreateDate().toString());
        orderParamMap.put(LAST_UPDATE_DATE, orderDTO.getLastUpdateDate().toString());
        return orderParamMap;
    }
}
