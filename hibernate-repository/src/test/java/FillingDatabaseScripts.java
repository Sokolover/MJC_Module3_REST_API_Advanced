package com.epam.esm.sokolov.service;

import com.epam.esm.sokolov.dto.GiftCertificateDTO;
import com.epam.esm.sokolov.dto.OrderDTO;
import com.epam.esm.sokolov.dto.UserDTO;
import com.epam.esm.sokolov.model.GiftCertificate;
import com.epam.esm.sokolov.service.order.OrderServiceImpl;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Query;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

class FillingDatabaseScripts {

    @Autowired
    OrderServiceImpl orderService;
    @Autowired
    SessionFactory sessionFactory;

    /*
        filling user_order and user_order_has_gift_certificate table script
     */
    public void fillOrderTableAndCreateOrders() {

        for (long i = 1; i <= 1000; i++) {
            int numOfOrders = (int) (Math.random() * 5 + 1);
            for (int j = 0; j < numOfOrders; j++) {
                OrderDTO orderDTO = new OrderDTO();

                UserDTO userDTO = new UserDTO();
                userDTO.setId(i);

                int numOfGiftCertificatesInOrder = (int) (Math.random() * 5 + 1);
                Set<Long> giftCertificateIds = new HashSet<>();
                Set<GiftCertificateDTO> giftCertificateDTOS = new HashSet<>();
                for (int k = 0; k < numOfGiftCertificatesInOrder; k++) {
                    GiftCertificateDTO giftCertificateDTO = new GiftCertificateDTO();
                    long gcId;
                    do {
                        gcId = (long) (Math.random() * 10000 + 1);
                    } while (!giftCertificateIds.add(gcId) || gcId < 1 || gcId > 10000);
                    giftCertificateDTO.setId(gcId);
                    giftCertificateDTOS.add(giftCertificateDTO);
                }

                orderDTO.setGiftCertificateDTOs(giftCertificateDTOS);
                orderDTO.setUserDTO(userDTO);
                orderService.save(orderDTO);
            }
        }

    }

    /*
      filling tag_has_gift_certificate table script
     */
    public void fillTagHasGiftCertificateTable() {
        for (int i = 0; i < 10000; i++) {
            Set<Integer> tagIdSet = new HashSet<>();
            int numOfTags = (int) (Math.random() * 5 + 1);
            for (int j = 0; j < numOfTags; j++) {
                Query query = sessionFactory.getCurrentSession().createSQLQuery(
                        "INSERT INTO module3.tag_has_gift_certificate " +
                                "(tag_id, gift_certificate_id) " +
                                "VALUES (:tagId, :gcId)"
                );
                int tagId;
                do {
                    tagId = (int) (Math.random() * 1000 + 1);
                } while (!tagIdSet.add(tagId) || tagId < 1 || tagId > 1000);
                query.setParameter("tagId", tagId);
                query.setParameter("gcId", i);
                query.executeUpdate();
            }
        }
    }

    /*
      filling gift_certificate table script
     */
    public void fillGiftCertificateTable() {
        for (int i = 0; i < 10000; i++) {
            GiftCertificate giftCertificate = new GiftCertificate();
            giftCertificate.setName("giftCertificate" + i);
            giftCertificate.setDescription("certificate " + "#" + i + " for " + (int) (Math.random() * 10 + 1) + " gift(s)");
            giftCertificate.setPrice(new BigDecimal(String.format("%s.%s", (int) (Math.random() * 20 + 1), (int) (Math.random() * 20 + 1))));
            giftCertificate.setCreateDate(LocalDateTime.now());
            giftCertificate.setCreateDateTimeZone(ZonedDateTime.now().getOffset().toString());
            giftCertificate.setLastUpdateDate(LocalDateTime.now());
            giftCertificate.setLastUpdateDateTimeZone(ZonedDateTime.now().getOffset().toString());
            giftCertificate.setDurationInDays((int) (Math.random() * 30 + 1));
            sessionFactory.getCurrentSession().save(giftCertificate);
        }
    }

    /*
      filling tag table script
     */
    public void fillTagTable() {
        for (int i = 0; i < 1000; i++) {
            GiftCertificate giftCertificate = new GiftCertificate();
            giftCertificate.setName("giftCertificate" + i);
            giftCertificate.setDescription("certificate " + "#" + i + " for " + (int) (Math.random() * 10 + 1) + " gift(s)");
            giftCertificate.setPrice(new BigDecimal(String.format("%s.%s", (int) (Math.random() * 20 + 1), (int) (Math.random() * 20 + 1))));
            giftCertificate.setCreateDate(LocalDateTime.now());
            giftCertificate.setCreateDateTimeZone(ZonedDateTime.now().getOffset().toString());
            giftCertificate.setLastUpdateDate(LocalDateTime.now());
            giftCertificate.setLastUpdateDateTimeZone(ZonedDateTime.now().getOffset().toString());
            giftCertificate.setDurationInDays((int) (Math.random() * 30 + 1));
            System.out.println(giftCertificate);
        }
    }
}
