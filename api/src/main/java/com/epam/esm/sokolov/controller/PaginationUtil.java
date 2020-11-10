package com.epam.esm.sokolov.controller;

import com.epam.esm.sokolov.dto.OrderDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
class PaginationUtil {

    void addPaginationLinks(
            Long userId,
            CollectionModel<OrderDTO> orderDTOS,
            final Long page,
            final Long totalPages,
            final Long pageSize) {

        if (hasNextPage(page, totalPages)) {
            orderDTOS.add(linkTo(methodOn(UserController.class).findAllOrders(userId, pageSize, page + 1)).withSelfRel().withName("nextPage"));
        }
        if (hasPreviousPage(page)) {
            orderDTOS.add(linkTo(methodOn(UserController.class).findAllOrders(userId, pageSize, page - 1)).withSelfRel().withName("prevPage"));
        }
        if (hasFirstPage(page)) {
            orderDTOS.add(linkTo(methodOn(UserController.class).findAllOrders(userId, pageSize, 0L)).withSelfRel().withName("firstPage"));
        }
        if (hasLastPage(page, totalPages)) {
            orderDTOS.add(linkTo(methodOn(UserController.class).findAllOrders(userId, pageSize, totalPages - 1)).withSelfRel().withName("lastPage"));
        }
    }

    private boolean hasNextPage(final Long page, final Long totalPages) {
        return page < totalPages - 1;
    }

    private boolean hasPreviousPage(final Long page) {
        return page > 0;
    }

    private boolean hasFirstPage(final Long page) {
        return hasPreviousPage(page);
    }

    private boolean hasLastPage(final Long page, final Long totalPages) {
        return totalPages > 1 && hasNextPage(page, totalPages);
    }
}
