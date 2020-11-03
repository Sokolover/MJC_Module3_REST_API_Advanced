package com.epam.esm.sokolov.controller;

import com.epam.esm.sokolov.dto.GiftCertificateDTO;
import com.epam.esm.sokolov.service.certificate.GiftCertificateService;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/gift_certificate")
@Api(value = "GiftCertificateControllerApi", produces = MediaType.APPLICATION_JSON_VALUE)
public class GiftCertificateController {

    private GiftCertificateService giftCertificateService;

    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping("/search")
    public List<GiftCertificateDTO> findAll(@RequestParam Map<String, String> allParams) {
        return giftCertificateService.findAllByParams(allParams);
    }

    @GetMapping
    public List<GiftCertificateDTO> findAll() {
        return giftCertificateService.findAll();
    }

    @GetMapping("/{id}")
    public GiftCertificateDTO findById(@PathVariable Long id) {
        return giftCertificateService.findById(id);
    }

    @PostMapping
    public void create(@RequestBody GiftCertificateDTO giftCertificate) {
        giftCertificateService.create(giftCertificate);
    }

    @PutMapping("/update")
    public void update(@RequestBody GiftCertificateDTO giftCertificate) {
        giftCertificateService.update(giftCertificate);
    }
//todo логгеры и возвращать что-то в update create
    @DeleteMapping("/delete")
    public void delete(@RequestBody GiftCertificateDTO giftCertificate) {
        giftCertificateService.delete(giftCertificate);
    }
}
