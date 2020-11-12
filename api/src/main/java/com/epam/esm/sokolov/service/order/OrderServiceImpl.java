package com.epam.esm.sokolov.service.order;

import com.epam.esm.sokolov.converter.OrderConverter;
import com.epam.esm.sokolov.dto.OrderDTO;
import com.epam.esm.sokolov.dto.OrderDetailsDTO;
import com.epam.esm.sokolov.exception.ServiceException;
import com.epam.esm.sokolov.model.GiftCertificate;
import com.epam.esm.sokolov.model.Order;
import com.epam.esm.sokolov.repository.certificate.GiftCertificateRepository;
import com.epam.esm.sokolov.repository.order.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.sokolov.constants.CommonAppConstants.INCORRECT_PAGE_SIZE_MESSAGE;

@Service
@Transactional
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
    public OrderDetailsDTO findOneOrderByUserIdAndOrderId(Long userId, Long orderId) {
        Order order = orderRepository.findOneOrderByUserIdAndOrderId(userId, orderId)
                .<ServiceException>orElseThrow(() -> {
                    String message = String.format("Resource not found (order id = %s)", orderId);
                    throw new ServiceException(message, HttpStatus.NOT_FOUND, this.getClass());
                });
        OrderDTO orderDTO = orderConverter.convert(order);
        return populateOrderParamMap(orderDTO);
    }

    @Override
    public OrderDTO save(OrderDTO orderDTO) {
        Order orderToSave = orderConverter.convert(orderDTO);
        setOrderCost(orderToSave);
        setCurrentTimeToOrder(orderToSave);
        Order savedOrder = orderRepository.save(orderToSave);
        return orderConverter.convert(savedOrder);
    }

    @Override
    public List<OrderDTO> findAllOrdersByUserId(Long id, Long pageSize, Long pageNumber) {
        if (isIncorrectArguments(pageSize, pageNumber)) {
            throw new ServiceException(INCORRECT_PAGE_SIZE_MESSAGE, HttpStatus.BAD_REQUEST, this.getClass());
        }
        Long pageOffsetInQuery = pageNumber * pageSize;
        List<Order> orders = orderRepository.findAllByUserAccountId(id, pageSize, pageOffsetInQuery);
        if (CollectionUtils.isEmpty(orders)) {
            throw new ServiceException(INCORRECT_PAGE_SIZE_MESSAGE, HttpStatus.BAD_REQUEST, this.getClass());
        }
        return orders.stream()
                .map(order -> orderConverter.convert(order))
                .collect(Collectors.toList());
    }

    @Override
    public Long findOrderAmountByUserId(Long id) {
        return orderRepository.findOrderAmountByUserId(id);
    }

    private boolean isIncorrectArguments(Long pageSize, Long pageNumber) {
        return pageSize == null || pageNumber == null || pageSize < 0 || pageNumber < 0;
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
        List<Long> giftCertificateIds = orderToSave.getGiftCertificates()
                .stream()
                .map(GiftCertificate::getId)
                .collect(Collectors.toList());
        BigDecimal orderCost = giftCertificateRepository.findAllById(giftCertificateIds)
                .stream()
                .map(GiftCertificate::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        orderToSave.setCost(orderCost);
    }

    private OrderDetailsDTO populateOrderParamMap(OrderDTO orderDTO) {
        OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO();
        orderDetailsDTO.setCost(orderDTO.getCost());
        orderDetailsDTO.setCreateDate(orderDTO.getCreateDate());
        orderDetailsDTO.setLastUpdateDate(orderDTO.getLastUpdateDate());
        return orderDetailsDTO;
    }
}
