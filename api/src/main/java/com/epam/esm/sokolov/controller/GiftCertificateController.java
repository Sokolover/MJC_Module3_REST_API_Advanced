package com.epam.esm.sokolov.controller;

import com.epam.esm.sokolov.dto.GiftCertificateDTO;
import com.epam.esm.sokolov.service.certificate.GiftCertificateService;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.epam.esm.sokolov.constants.CommonAppConstants.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/gift-certificates")
@Api(value = "GiftCertificateControllerApi", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;
    private final PaginationUtil paginationUtil;

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDTO update(@PathVariable Long id, @Valid @RequestBody GiftCertificateDTO giftCertificateDTO) {
        GiftCertificateDTO updatedGiftCertificateDTO = giftCertificateService.update(id, giftCertificateDTO);
        updatedGiftCertificateDTO
                .add(linkTo(methodOn(GiftCertificateController.class)
                        .update(id, giftCertificateDTO)).withSelfRel().withType("PATCH"))
                .add(linkTo(methodOn(GiftCertificateController.class)
                        .findByTagNames("null", new ArrayList<>(Collections.singleton("null")), DEFAULT_PAGE_SIZE, DEFAULT_PAGE_NUMBER)).withSelfRel());
        return updatedGiftCertificateDTO;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<GiftCertificateDTO> findByTagNames(@RequestParam String searchBy,
                                                              @RequestParam("tagName") List<String> tagNames,
                                                              @RequestParam("size") Long pageSize,
                                                              @RequestParam("page") Long pageNumber
    ) {
        if (!TAG_NAMES_SEARCH_PARAM.equals(searchBy)) {
            return CollectionModel.of(new ArrayList<>());
        }
        List<GiftCertificateDTO> giftCertificateDTOS = giftCertificateService.findByTagNames(tagNames, pageSize, pageNumber);
        giftCertificateDTOS.forEach(giftCertificateDTO -> giftCertificateDTO
                .add(linkTo(methodOn(GiftCertificateController.class)
                        .update(giftCertificateDTO.getId(), giftCertificateDTO)).withSelfRel().withType("PATCH"))
        );
        Link link = linkTo(methodOn(GiftCertificateController.class)
                .findByTagNames(searchBy, tagNames, pageSize, pageNumber)).withSelfRel();
        CollectionModel<GiftCertificateDTO> collectionModelGiftCertificateDTOS = CollectionModel.of(giftCertificateDTOS, link);
        Long totalPages = giftCertificateService.findGiftCertificateAmountByTagNames(tagNames) / pageSize;
        paginationUtil.addPaginationLinksToGiftCertificateDTO(
                searchBy,
                tagNames,
                collectionModelGiftCertificateDTOS,
                pageNumber,
                totalPages,
                pageSize
        );
        return collectionModelGiftCertificateDTOS;
    }
}
