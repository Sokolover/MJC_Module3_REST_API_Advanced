package com.epam.esm.sokolov.controller;

import com.epam.esm.sokolov.dto.OrderDTO;
import com.epam.esm.sokolov.service.order.OrderService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/orders")
@Api(value = "OrderControllerApi", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO save(@RequestBody OrderDTO orderDTO) {
        OrderDTO savedOrderDTO = orderService.save(orderDTO);
        return savedOrderDTO.add(linkTo(methodOn(OrderController.class)
                .save(orderDTO)).withSelfRel().withType("POST"));
    }
}
