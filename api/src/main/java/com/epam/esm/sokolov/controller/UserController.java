package com.epam.esm.sokolov.controller;

import com.epam.esm.sokolov.dto.OrderDTO;
import com.epam.esm.sokolov.service.user.UserService;
import io.swagger.annotations.Api;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@Api(value = "UserControllerApi", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private UserService userService;
    private PaginationUtil paginationUtil;

    public UserController(UserService userService, PaginationUtil paginationUtil) {
        this.userService = userService;
        this.paginationUtil = paginationUtil;
    }

    @GetMapping("/{id}/orders")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<OrderDTO> findAllOrders(@PathVariable Long id,
                                                   @RequestParam("size") Long pageSize,
                                                   @RequestParam("page") Long pageNumber
    ) {//todo tell if page size is Long or Integer
        List<OrderDTO> orderDTOS = userService.findAllOrdersByUserId(id, pageSize, pageNumber);
        CollectionModel<OrderDTO> collectionModelOrderDTOs = CollectionModel.of(orderDTOS);
        Long totalPages = userService.findOrderAmountByUserId(id) / pageSize;
        paginationUtil.addPaginationLinksToOrderDTO(
                id,
                collectionModelOrderDTOs,
                pageNumber,
                totalPages,
                pageSize
        );
        return collectionModelOrderDTOs;
    }

    @GetMapping("/{id}/orders/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> findAllOrdersByUserIdAndOrderId(@PathVariable Long id, @PathVariable Long orderId) {
        return userService.findOneOrderByUserIdAndOrderId(id, orderId);
    }

    //    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public UserDTO save(@RequestBody UserDTO user) {
//        return userService.save(user);
//    }
//
//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public CollectionModel<UserDTO> findAll() {
//        List<UserDTO> userDTOS = userService.findAll();
//        userDTOS.forEach(userDTO -> userDTO
//                .add(linkTo(methodOn(UserController.class).findAllOrders(userDTO.getId(), DEFAULT_PAGE_SIZE, DEFAULT_PAGE_NUMBER)).withSelfRel())
//        );
//        Link link = linkTo(UserController.class).withSelfRel();
//        return CollectionModel.of(userDTOS, link);
//    }
}
