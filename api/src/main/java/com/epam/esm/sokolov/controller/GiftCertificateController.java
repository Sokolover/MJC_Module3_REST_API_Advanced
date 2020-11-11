package com.epam.esm.sokolov.controller;

import com.epam.esm.sokolov.dto.GiftCertificateDTO;
import com.epam.esm.sokolov.service.certificate.GiftCertificateService;
import io.swagger.annotations.Api;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.epam.esm.sokolov.constants.CommonAppConstants.DEFAULT_PAGE_NUMBER;
import static com.epam.esm.sokolov.constants.CommonAppConstants.DEFAULT_PAGE_SIZE;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/gift-certificates")
@Api(value = "GiftCertificateControllerApi", produces = MediaType.APPLICATION_JSON_VALUE)
public class GiftCertificateController {

    private GiftCertificateService giftCertificateService;
    private PaginationUtil paginationUtil;

    public GiftCertificateController(GiftCertificateService giftCertificateService, PaginationUtil paginationUtil) {
        this.giftCertificateService = giftCertificateService;
        this.paginationUtil = paginationUtil;
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDTO update(@PathVariable Long id, @RequestBody GiftCertificateDTO giftCertificateDTO) {
        GiftCertificateDTO updatedGiftCertificateDTO = giftCertificateService.update(id, giftCertificateDTO);
        updatedGiftCertificateDTO
                .add(linkTo(methodOn(GiftCertificateController.class)
                        .update(id, giftCertificateDTO)).withSelfRel().withType("PATCH"))
                .add(linkTo(methodOn(GiftCertificateController.class)
                        .findByTagNames(new ArrayList<>(Collections.singleton("null")), DEFAULT_PAGE_SIZE, DEFAULT_PAGE_NUMBER)).withSelfRel());
        return updatedGiftCertificateDTO;
    }

    @GetMapping("/tag-names")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<GiftCertificateDTO> findByTagNames(@RequestParam(name = "tagName") List<String> tagNames,
                                                              @RequestParam("size") Long pageSize,
                                                              @RequestParam("page") Long pageNumber
    ) {
        List<GiftCertificateDTO> giftCertificateDTOS = giftCertificateService.findByTagNames(tagNames, pageSize, pageNumber);
        giftCertificateDTOS.forEach(giftCertificateDTO -> giftCertificateDTO
                .add(linkTo(methodOn(GiftCertificateController.class)
                        .update(giftCertificateDTO.getId(), giftCertificateDTO)).withSelfRel().withType("PATCH"))
        );
        Link link = linkTo(methodOn(GiftCertificateController.class)
                .findByTagNames(tagNames, pageSize, pageNumber)).withSelfRel();
        CollectionModel<GiftCertificateDTO> collectionModelGiftCertificateDTOS = CollectionModel.of(giftCertificateDTOS, link);
        Long totalPages = giftCertificateService.findGiftCertificateAmountByTagNames(tagNames) / pageSize;
        paginationUtil.addPaginationLinksToGiftCertificateDTO(
                tagNames,
                collectionModelGiftCertificateDTOS,
                pageNumber,
                totalPages,
                pageSize
        );
        return collectionModelGiftCertificateDTOS;
    }
}
