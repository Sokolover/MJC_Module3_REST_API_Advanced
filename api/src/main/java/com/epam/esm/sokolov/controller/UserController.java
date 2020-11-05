package com.epam.esm.sokolov.controller;

import com.epam.esm.sokolov.dto.OrderDTO;
import com.epam.esm.sokolov.dto.UserDTO;
import com.epam.esm.sokolov.service.user.UserService;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping
    public List<UserDTO> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}/orders")
    public List<OrderDTO> findAllOrders(@PathVariable Long id) {
        return userService.findAllOrdersByUserId(id);
    }

    @GetMapping("/{id}/orders/{orderId}")
    public ResponseEntity<Map<String, String>> findAllOrders(@PathVariable Long id, @PathVariable Long orderId) {
        /*
        todo talk with mentor if it's ok solution that I done here
               /{id}/orders/{orderId} -> because orderId is UNIQUE IN DB
            @
            return ResponseEntity<Map<String, String>>, just map with purcaseAndTimestamp and thats all
         */
        return userService.findOneOrderByUserIdAndOrderId(id, orderId);
    }
}
