package com.epam.esm.sokolov.controller;

import com.epam.esm.sokolov.dto.GiftCertificateDTO;
import com.epam.esm.sokolov.service.certificate.GiftCertificateService;
import io.swagger.annotations.Api;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return giftCertificateService.update(id, giftCertificateDTO);
    }

    @GetMapping("/tag-names")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<GiftCertificateDTO> findByTagNames(@RequestParam(name = "tagName") List<String> tagNames,
                                                              @RequestParam("size") Long pageSize,
                                                              @RequestParam("page") Long pageNumber
    ) {
        List<GiftCertificateDTO> giftCertificateDTOS = giftCertificateService.findByTagNames(tagNames, pageSize, pageNumber);
        CollectionModel<GiftCertificateDTO> collectionModelGiftCertificateDTOS = CollectionModel.of(giftCertificateDTOS);
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

//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public List<GiftCertificateDTO> findAll() {
//        return giftCertificateService.findAll();
//    }
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public GiftCertificateDTO save(@RequestBody GiftCertificateDTO giftCertificateDTO) {
//        return giftCertificateService.save(giftCertificateDTO);
//    }
}
