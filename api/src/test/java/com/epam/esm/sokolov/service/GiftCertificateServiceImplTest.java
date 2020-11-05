//package com.epam.esm.sokolov.service;
//
//import com.epam.esm.sokolov.dto.GiftCertificateDTO;
//import com.epam.esm.sokolov.model.GiftCertificate;
//import com.epam.esm.sokolov.service.certificate.GiftCertificateServiceImpl;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.math.BigDecimal;
//import java.time.ZonedDateTime;
//import java.util.HashSet;
//import java.util.List;
//
//@SpringBootTest
//@ActiveProfiles("test")
//class GiftCertificateServiceImplTest {
//
//    @Autowired
//    private GiftCertificateServiceImpl giftCertificateServiceImpl;
//
//    @Test
//    void shouldUpdateWithoutException() {
//
//        List<GiftCertificate> giftCertificates = giftCertificateServiceImpl.findAll();
//
//        GiftCertificateDTO giftCertificateDTO = giftCertificatesFromController();
//        GiftCertificateDTO giftCertificateDTOfromService = giftCertificateServiceImpl.update(giftCertificateDTO);
//        System.out.println(giftCertificateDTOfromService);
//    }
//
//    private GiftCertificateDTO giftCertificatesFromController() {
//        return new GiftCertificateDTO(15L,
//                "netflix",
//                "5 any films",
//                new BigDecimal("5.55"),
//                ZonedDateTime.parse("2020-10-23T09:37:39+03:00"),
//                ZonedDateTime.parse("2020-10-23T14:37:39+03:00"),
//                10,
//                new HashSet<>());
//    }
//}