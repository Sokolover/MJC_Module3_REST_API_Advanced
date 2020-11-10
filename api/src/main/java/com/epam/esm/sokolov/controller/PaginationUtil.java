package com.epam.esm.sokolov.controller;

import com.epam.esm.sokolov.dto.GiftCertificateDTO;
import com.epam.esm.sokolov.dto.OrderDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
class PaginationUtil {

    private static final String NEXT_PAGE = "nextPage";
    private static final String PREV_PAGE = "prevPage";
    private static final String FIRST_PAGE = "firstPage";
    private static final String LAST_PAGE = "lastPage";

    void addPaginationLinksToGiftCertificateDTO(
            List<String> tagNames,
            CollectionModel<GiftCertificateDTO> giftCertificateDTOS,
            final Long pageNumber,
            final Long totalPages,
            final Long pageSize) {

        if (hasNextPage(pageNumber, totalPages)) {
            giftCertificateDTOS.add(getLinkToFindAllByTagNamesMethod(tagNames, pageSize, pageNumber + 1, NEXT_PAGE));
        }
        if (hasPreviousPage(pageNumber)) {
            giftCertificateDTOS.add(getLinkToFindAllByTagNamesMethod(tagNames, pageSize, pageNumber - 1, PREV_PAGE));
        }
        if (hasFirstPage(pageNumber)) {
            giftCertificateDTOS.add(getLinkToFindAllByTagNamesMethod(tagNames, pageSize, 0L, FIRST_PAGE));
        }
        if (hasLastPage(pageNumber, totalPages)) {
            giftCertificateDTOS.add(getLinkToFindAllByTagNamesMethod(tagNames, pageSize, totalPages - 1, LAST_PAGE));
        }
    }

    void addPaginationLinksToOrderDTO(
            Long userId,
            CollectionModel<OrderDTO> orderDTOS,
            final Long pageNumber,
            final Long totalPages,
            final Long pageSize) {

        if (hasNextPage(pageNumber, totalPages)) {
            orderDTOS.add(getLinkToFindAllOrdersMethod(userId, pageSize, pageNumber + 1, NEXT_PAGE));
        }
        if (hasPreviousPage(pageNumber)) {
            orderDTOS.add(getLinkToFindAllOrdersMethod(userId, pageSize, pageNumber - 1, PREV_PAGE));
        }
        if (hasFirstPage(pageNumber)) {
            orderDTOS.add(getLinkToFindAllOrdersMethod(userId, pageSize, 0L, FIRST_PAGE));
        }
        if (hasLastPage(pageNumber, totalPages)) {
            orderDTOS.add(getLinkToFindAllOrdersMethod(userId, pageSize, totalPages - 1, LAST_PAGE));
        }
    }

    private Link getLinkToFindAllByTagNamesMethod(List<String> tagNames, Long pageSize, Long pageNumber, String linkName) {
        return linkTo(methodOn(GiftCertificateController.class).findByTagNames(tagNames, pageSize, pageNumber)).withSelfRel().withName(linkName);
    }

    private Link getLinkToFindAllOrdersMethod(Long id, Long pageSize, Long pageNumber, String linkName) {
        return linkTo(methodOn(UserController.class).findAllOrders(id, pageSize, pageNumber)).withSelfRel().withName(linkName);
    }

    private boolean hasNextPage(final Long pageNumber, final Long totalPages) {
        return pageNumber < totalPages - 1;
    }

    private boolean hasPreviousPage(final Long pageNumber) {
        return pageNumber > 0;
    }

    private boolean hasFirstPage(final Long pageNumber) {
        return hasPreviousPage(pageNumber);
    }

    private boolean hasLastPage(final Long pageNumber, final Long totalPages) {
        return totalPages > 1 && hasNextPage(pageNumber, totalPages);
    }
}
