package com.epam.esm.sokolov.controller;

import com.epam.esm.sokolov.dto.OrderDTO;
import com.epam.esm.sokolov.dto.UserDTO;
import com.epam.esm.sokolov.service.user.UserService;
import io.swagger.annotations.Api;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/users")
@Api(value = "UserControllerApi", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private static final Long DEFAULT_PAGE_SIZE = 10L;
    private static final Long DEFAULT_PAGE_NUMBER = 0L;

    private UserService userService;
    private PaginationUtil paginationUtil;

    public UserController(UserService userService, PaginationUtil paginationUtil) {
        this.userService = userService;
        this.paginationUtil = paginationUtil;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO save(@RequestBody UserDTO user) {
        return userService.save(user);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<UserDTO> findAll() {
        List<UserDTO> userDTOS = userService.findAll();
        userDTOS.forEach(userDTO -> userDTO
                .add(linkTo(methodOn(UserController.class).findAllOrders(userDTO.getId(), DEFAULT_PAGE_SIZE, DEFAULT_PAGE_NUMBER)).withSelfRel())
        );
        Link link = linkTo(UserController.class).withSelfRel();
        return CollectionModel.of(userDTOS, link);
    }

    @GetMapping("/{id}/orders")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<OrderDTO> findAllOrders(@PathVariable Long id,
                                                   @RequestParam Long size,
                                                   @RequestParam Long page
    ) {//todo tell if page size is Long or Integer

        List<OrderDTO> orderDTOS = userService.findAllOrdersByUserId(id, size, page);
        CollectionModel<OrderDTO> collectionModelOrderDTOs = CollectionModel.of(orderDTOS);
        paginationUtil.addPaginationLinks(
                id,
                collectionModelOrderDTOs,
                page,
                userService.findOrderAmountByUserId(id) / size,
                size
        );
        return collectionModelOrderDTOs;
    }

    @GetMapping("/{id}/orders/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> findAllOrdersByUserIdAndOrderId(@PathVariable Long id, @PathVariable Long orderId) {
        return userService.findOneOrderByUserIdAndOrderId(id, orderId);
    }
}
