package com.epam.esm.sokolov.controller;

import com.epam.esm.sokolov.dto.OrderDTO;
import com.epam.esm.sokolov.dto.UserDTO;
import com.epam.esm.sokolov.model.User;
import com.epam.esm.sokolov.service.user.UserService;
import io.swagger.annotations.Api;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public UserDTO save(@RequestBody UserDTO user) {
        return userService.save(user);
    }

    @GetMapping//todo 5)
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<UserDTO> findAll() {
        List<UserDTO> userDTOS = userService.findAll();
        userDTOS.forEach(userDTO -> userDTO
                .add(linkTo(methodOn(UserController.class).findAllOrders(userDTO.getId()))
                        .withSelfRel()));

        Link link = linkTo(UserController.class).withSelfRel();
        return CollectionModel.of(userDTOS, link);
    }

//    @GetMapping(produces = { "application/hal+json" })
//    public CollectionModel<Customer> getAllCustomers() {
//        List<Customer> allCustomers = customerService.allCustomers();
//
//        for (Customer customer : allCustomers) {
//            String customerId = customer.getCustomerId();
//            Link selfLink = linkTo(CustomerController.class).slash(customerId).withSelfRel();
//            customer.add(selfLink);
//            if (orderService.getAllOrdersForCustomer(customerId).size() > 0) {
//                Link ordersLink = linkTo(methodOn(CustomerController.class)
//                        .getOrdersForCustomer(customerId)).withRel("allOrders");
//                customer.add(ordersLink);
//            }
//        }
//
//        Link link = linkTo(CustomerController.class).withSelfRel();
//        CollectionModel<Customer> result = new CollectionModel<>(allCustomers, link);
//        return result;
//    }

    @GetMapping("/{id}/orders")
    public List<OrderDTO> findAllOrders(@PathVariable Long id) {
        return userService.findAllOrdersByUserId(id);
    }

    @GetMapping("/{id}/orders/{orderId}")//todo 1)
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> findAllOrders(@PathVariable Long id, @PathVariable Long orderId) {
        /*
        todo talk with mentor if it's ok solution that I done here
               /{id}/orders/{orderId} -> because orderId is UNIQUE IN DB
            @
            return ResponseEntity<Map<String, String>>, just map with purcaseAndTimestamp and thats all
         */
        return userService.findOneOrderByUserIdAndOrderId(id, orderId);
    }


}
