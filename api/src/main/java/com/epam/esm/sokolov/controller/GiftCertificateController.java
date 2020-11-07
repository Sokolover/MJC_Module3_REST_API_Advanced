package com.epam.esm.sokolov.controller;

import com.epam.esm.sokolov.dto.GiftCertificateDTO;
import com.epam.esm.sokolov.service.certificate.GiftCertificateService;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gift-certificates")
@Api(value = "GiftCertificateControllerApi", produces = MediaType.APPLICATION_JSON_VALUE)
public class GiftCertificateController {

    private GiftCertificateService giftCertificateService;

    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @PatchMapping
    public GiftCertificateDTO update(@RequestBody GiftCertificateDTO giftCertificateDTO) {
        return giftCertificateService.update(giftCertificateDTO);
    }

    @GetMapping
    public List<GiftCertificateDTO> findAll() {
        return giftCertificateService.findAll();
    }

    @PostMapping//todo make status codes (201 create)
    public GiftCertificateDTO save(@RequestBody GiftCertificateDTO giftCertificateDTO) {
        return giftCertificateService.save(giftCertificateDTO);
    }

    //    https://www.baeldung.com/spring-request-param
    // todo 4)
    @GetMapping("/tag-names")//todo ask if it is ok mapping?
    public List<GiftCertificateDTO> findAllByTagNames(@RequestParam(name = "tagName") List<String> tagNames) {
        return giftCertificateService.findAllByTagNames(tagNames);
    }
}
