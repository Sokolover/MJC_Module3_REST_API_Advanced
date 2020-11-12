package com.epam.esm.sokolov.controller;

import com.epam.esm.sokolov.dto.OrderDTO;
import com.epam.esm.sokolov.dto.OrderDTODetails;
import com.epam.esm.sokolov.dto.UserDTO;
import com.epam.esm.sokolov.service.order.OrderService;
import com.epam.esm.sokolov.service.user.UserService;
import io.swagger.annotations.Api;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.epam.esm.sokolov.constants.CommonAppConstants.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/users")
@Api(value = "UserControllerApi", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private UserService userService;
    private OrderService orderService;
    private PaginationUtil paginationUtil;

    public UserController(UserService userService, OrderService orderService, PaginationUtil paginationUtil) {
        this.userService = userService;
        this.orderService = orderService;
        this.paginationUtil = paginationUtil;
    }

    @GetMapping("/{id}/orders")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<OrderDTO> findAllOrders(@PathVariable Long id,
                                                   @RequestParam("size") Long pageSize,
                                                   @RequestParam("page") Long pageNumber
    ) {
        List<OrderDTO> orderDTOS = orderService.findAllOrdersByUserId(id, pageSize, pageNumber);
        orderDTOS.forEach(orderDTO -> orderDTO
                .add(linkTo(methodOn(UserController.class)
                        .findOrderByUserIdAndOrderId(id, orderDTO.getId())).withSelfRel())
                .add(linkTo(methodOn(UserController.class)
                        .findAll(DEFAULT_PAGE_SIZE, DEFAULT_PAGE_NUMBER)).withRel(USERS_REF))
        );
        Link link = linkTo(methodOn(UserController.class)
                .findAllOrders(id, DEFAULT_PAGE_SIZE, DEFAULT_PAGE_NUMBER)).withSelfRel();
        CollectionModel<OrderDTO> orderDTOCollectionModel = CollectionModel.of(orderDTOS, link);
        Long totalPages = orderService.findOrderAmountByUserId(id) / pageSize;
        paginationUtil.addPaginationLinksToOrderDTO(
                id,
                orderDTOCollectionModel,
                pageNumber,
                totalPages,
                pageSize
        );
        return orderDTOCollectionModel;
    }

    @GetMapping("/{id}/orders/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTODetails findOrderByUserIdAndOrderId(@PathVariable Long id, @PathVariable Long orderId) {
        OrderDTODetails orderDTODetails = orderService.findOneOrderByUserIdAndOrderId(id, orderId);
        orderDTODetails
                .add(linkTo(methodOn(UserController.class)
                        .findAll(DEFAULT_PAGE_SIZE, DEFAULT_PAGE_NUMBER)).withRel(USERS_REF))
                .add(linkTo(methodOn(UserController.class)
                        .findAllOrders(id, DEFAULT_PAGE_SIZE, DEFAULT_PAGE_NUMBER)).withSelfRel())
                .add(linkTo(methodOn(UserController.class)
                        .findOrderByUserIdAndOrderId(id, orderId)).withSelfRel());
        return orderDTODetails;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<UserDTO> findAll(@RequestParam("size") Long pageSize,
                                            @RequestParam("page") Long pageNumber
    ) {
        List<UserDTO> userDTOS = userService.findAll(pageSize, pageNumber);
        userDTOS.forEach(userDTO -> userDTO
                .add(linkTo(methodOn(UserController.class)
                        .findAllOrders(userDTO.getId(), DEFAULT_PAGE_SIZE, DEFAULT_PAGE_NUMBER)).withRel(ORDERS_REF))
        );
        Link link = linkTo(methodOn(UserController.class)
                .findAll(pageSize, pageNumber)).withRel(USERS_REF);
        CollectionModel<UserDTO> userDTOCollectionModel = CollectionModel.of(userDTOS, link);
        Long totalPages = userService.findUserAmount() / pageSize;
        paginationUtil.addPaginationLinksToUserDTO(
                userDTOCollectionModel,
                pageNumber,
                totalPages,
                pageSize
        );
        return userDTOCollectionModel;
    }
}
