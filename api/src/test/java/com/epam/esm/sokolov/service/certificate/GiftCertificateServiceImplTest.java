package com.epam.esm.sokolov.service.certificate;

import com.epam.esm.sokolov.converter.GiftCertificateConverter;
import com.epam.esm.sokolov.converter.TagConverter;
import com.epam.esm.sokolov.dto.GiftCertificateDTO;
import com.epam.esm.sokolov.dto.TagDTO;
import com.epam.esm.sokolov.model.GiftCertificate;
import com.epam.esm.sokolov.model.Tag;
import com.epam.esm.sokolov.repository.GiftCertificateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;
    @Mock
    private GiftCertificateRepository giftCertificateRepository;
    @Spy
    @InjectMocks
    private GiftCertificateConverter giftCertificateConverter = new GiftCertificateConverter();
    @Spy
    private GiftCertificateMapperImpl giftCertificateMapper;
    @Spy
    private TagConverter tagConverter;

    @Test
    void shouldUpdateGiftCertificateAllFieldsFromGiftCertificateDTO() {


        GiftCertificateDTO giftCertificateDTOFromController = createGiftCertificateFromController();
        GiftCertificate giftCertificateFromDatabase = createGiftCertificateFromDatabase();
        Mockito.doReturn(Optional.of(giftCertificateFromDatabase)).when(giftCertificateRepository)
                .findById(giftCertificateDTOFromController.getId());
        GiftCertificate updatedGiftCertificateFromDatabase = createUpdatedGiftCertificateFromDatabase();
        Mockito.doReturn(updatedGiftCertificateFromDatabase).when(giftCertificateRepository)
                .save(updatedGiftCertificateFromDatabase);

        GiftCertificateDTO updatedGiftCertificateDTOFromController = giftCertificateService.update(giftCertificateDTOFromController.getId(), giftCertificateDTOFromController);
        Assertions.assertEquals(giftCertificateDTOFromController, updatedGiftCertificateDTOFromController);
    }

    private GiftCertificateDTO createGiftCertificateFromController() {
        GiftCertificateDTO giftCertificateDTO = new GiftCertificateDTO();
        giftCertificateDTO.setId(1L);
        giftCertificateDTO.setName("giftCertificate1_updated");
        giftCertificateDTO.setDescription("giftCertificate1Description_updated");
        giftCertificateDTO.setPrice(new BigDecimal("2"));
        giftCertificateDTO.setCreateDate(ZonedDateTime.parse("2020-10-23T09:00:00+03:00"));
        giftCertificateDTO.setLastUpdateDate(ZonedDateTime.parse("2020-10-23T14:00:00+03:00"));
        giftCertificateDTO.setDurationInDays(2);
        Set<TagDTO> tagDTOS = new HashSet<>();
        tagDTOS.add(new TagDTO(1L, "tag1upd"));
        giftCertificateDTO.setTags(tagDTOS);
        return giftCertificateDTO;
    }

    private GiftCertificate createGiftCertificateFromDatabase() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1L);
        giftCertificate.setName("giftCertificate1");
        giftCertificate.setDescription("giftCertificate1Description");
        giftCertificate.setPrice(new BigDecimal("1"));
        giftCertificate.setCreateDate(LocalDateTime.parse("2020-10-23T09:00:00"));
        giftCertificate.setCreateDateTimeZone("+03:00");
        giftCertificate.setLastUpdateDate(LocalDateTime.parse("2020-11-11T12:00:00"));
        giftCertificate.setLastUpdateDateTimeZone("+03:00");
        giftCertificate.setDurationInDays(1);
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag(1L, "tag1"));
        tags.add(new Tag(2L, "tag2"));
        giftCertificate.setTags(tags);
        return giftCertificate;
    }

    private GiftCertificate createUpdatedGiftCertificateFromDatabase() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1L);
        giftCertificate.setName("giftCertificate1_updated");
        giftCertificate.setDescription("giftCertificate1Description_updated");
        giftCertificate.setPrice(new BigDecimal("2"));
        giftCertificate.setCreateDate(LocalDateTime.parse("2020-10-23T06:00:00"));
        giftCertificate.setCreateDateTimeZone("+03:00");
        giftCertificate.setLastUpdateDate(LocalDateTime.parse("2020-10-23T11:00:00"));
        giftCertificate.setLastUpdateDateTimeZone("+03:00");
        giftCertificate.setDurationInDays(2);
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag(1L, "tag1upd"));
        giftCertificate.setTags(tags);
        return giftCertificate;
    }
}