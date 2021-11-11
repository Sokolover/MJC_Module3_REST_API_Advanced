package com.epam.esm.sokolov.converter;

import com.epam.esm.sokolov.model.GiftCertificate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * @author Sokolov_SA
 * @created 11.11.2021
 */
@ExtendWith(MockitoExtension.class)
class GiftCertificateConverterTest {

    @Spy
    private ModelMapper modelMapper;

    @Test
    void updateDatabaseGC_By_ControllerGC() {
        GiftCertificate databaseGC = getDatabaseGC();
        GiftCertificate controllerGC = getControllerGC();

        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.map(controllerGC, databaseGC);

        assertEquals("gc2", databaseGC.getName());
        assertEquals(11, databaseGC.getDurationInDays());
        assertNotEquals(null, databaseGC.getDescription());
        assertNotEquals(null, databaseGC.getCreateDate());
    }

    private GiftCertificate getDatabaseGC() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1L);
        giftCertificate.setName("gc1");
        giftCertificate.setDescription("11 any");
        giftCertificate.setPrice(new BigDecimal("10"));
        giftCertificate.setCreateDate(LocalDateTime.now().minusDays(1L));
        giftCertificate.setCreateDateTimeZone("+03:00");
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        giftCertificate.setLastUpdateDateTimeZone("+03:00");
        giftCertificate.setDurationInDays(10);
        return giftCertificate;
    }

    private GiftCertificate getControllerGC() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1L);
        giftCertificate.setName("gc2");
//        giftCertificate.setDescription("10 any");
        giftCertificate.setPrice(new BigDecimal("10"));
//        giftCertificate.setCreateDate(LocalDateTime.now().minusDays(2L));
//        giftCertificate.setCreateDateTimeZone("+03:00");
//        giftCertificate.setLastUpdateDate(LocalDateTime.now());
//        giftCertificate.setLastUpdateDateTimeZone("+03:00");
        giftCertificate.setDurationInDays(11);
        return giftCertificate;
    }
}
