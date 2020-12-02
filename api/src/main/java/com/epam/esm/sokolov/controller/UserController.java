package com.epam.esm.sokolov.controller;

import com.epam.esm.sokolov.dto.OrderDTO;
import com.epam.esm.sokolov.dto.OrderDetailsDTO;
import com.epam.esm.sokolov.dto.UserDTO;
import com.epam.esm.sokolov.service.order.OrderService;
import com.epam.esm.sokolov.service.user.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.epam.esm.sokolov.constants.CommonAppConstants.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/users")
@Api(value = "UserControllerApi", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserController {

    private final UserService userService;
    private final OrderService orderService;
    private final PaginationUtil paginationUtil;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO signUp(@RequestBody @Valid UserDTO userDTO) {
        return userService.save(userDTO);
    }

    @GetMapping("/{id}/orders")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN') && (principal.username == @userServiceImpl.findById(#id).username)")
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
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN') && (principal.username == @userServiceImpl.findById(#id).username)")
    @ResponseStatus(HttpStatus.OK)
    public OrderDetailsDTO findOrderByUserIdAndOrderId(@PathVariable Long id, @PathVariable Long orderId) {
        OrderDetailsDTO orderDetailsDTO = orderService.findOneOrderByUserIdAndOrderId(id, orderId);
        orderDetailsDTO
                .add(linkTo(methodOn(UserController.class)
                        .findAll(DEFAULT_PAGE_SIZE, DEFAULT_PAGE_NUMBER)).withRel(USERS_REF))
                .add(linkTo(methodOn(UserController.class)
                        .findAllOrders(id, DEFAULT_PAGE_SIZE, DEFAULT_PAGE_NUMBER)).withSelfRel())
                .add(linkTo(methodOn(UserController.class)
                        .findOrderByUserIdAndOrderId(id, orderId)).withSelfRel());
        return orderDetailsDTO;
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
