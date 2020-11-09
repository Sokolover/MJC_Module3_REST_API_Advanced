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

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
                .add(linkTo(methodOn(UserController.class).findAllOrders(userDTO.getId())).withSelfRel())
        );
        Link link = linkTo(UserController.class).withSelfRel();
        return CollectionModel.of(userDTOS, link);
    }

    @GetMapping("/{id}/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDTO> findAllOrders(@PathVariable Long id) {
        return userService.findAllOrdersByUserId(id);
    }

    @GetMapping("/{id}/orders/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> findAllOrdersByUserIdAndOrderId(@PathVariable Long id, @PathVariable Long orderId) {
        return userService.findOneOrderByUserIdAndOrderId(id, orderId);
    }
}
