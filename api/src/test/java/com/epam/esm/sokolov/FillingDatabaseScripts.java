package com.epam.esm.sokolov;

import com.epam.esm.sokolov.repository.user.UserRepository;
import com.epam.esm.sokolov.service.order.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Module3.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
//@RequiredArgsConstructor(onConstructor_ = @Autowired)
//@Sql(value = "classpath:drop-create-database.sql")
class FillingDatabaseScripts {

    @Autowired
    private OrderServiceImpl orderService;
    //    @Autowired
//    SessionFactory sessionFactory;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //        @Transactional
//    @Modifying(clearAutomatically = true)
//    @org.springframework.data.jpa.repository.Query(value = "INSERT INTO user_account_has_user_role (user_account_id, user_role_id) " +
//            "VALUES (:user_id, 1) ", nativeQuery = true)
//    void updateUsersRoles(@Param("user_id") Long id);

    @Test
    void addUsersRoles() {
        for (long i = 1; i <= 1000; i++) {
            try {
                userRepository.addUserRoles(i);
            } catch (Exception ignored) {
                // for skip ids that doesn't exist
            }
        }
    }

    @Test
    void updateUserPasswords() {
        for (long i = 1; i <= 1000; i++) {
            try {
                String encodedPassword = passwordEncoder.encode("1111");
                userRepository.updateUserPassword(i, encodedPassword);
            } catch (Exception ignored) {
                // for skip ids that doesn't exist
            }
        }
    }

//        @Transactional
//    @Modifying(clearAutomatically = true)
//    @org.springframework.data.jpa.repository.Query(value = "UPDATE user_account u " +
//            "SET u.password = :password1 " +
//            "WHERE u.id = :id ", nativeQuery = true)
//    void updateUserPassword(@Param("id") Long id,
//                            @Param("password1") String password);
//    public void updateUserPasswords() {
//        for (int i = 5; i < 1001; i++) {
//            long id = i;
//            String encodedPassword = passwordEncoder.encode("123");
//            userRepository.updateUserPassword(id, encodedPassword);
//        }
//    }

//    /*
//        filling user_order and user_order_has_gift_certificate table script
//     */
//    public void fillOrderTableAndCreateOrders() {
//
//        for (long i = 1; i <= 1000; i++) {
//            int numOfOrders = (int) (Math.random() * 5 + 1);
//            for (int j = 0; j < numOfOrders; j++) {
//                OrderDTO orderDTO = new OrderDTO();
//
//                UserDTO userDTO = new UserDTO();
//                userDTO.setId(i);
//
//                int numOfGiftCertificatesInOrder = (int) (Math.random() * 5 + 1);
//                Set<Long> giftCertificateIds = new HashSet<>();
//                Set<GiftCertificateDTO> giftCertificateDTOS = new HashSet<>();
//                for (int k = 0; k < numOfGiftCertificatesInOrder; k++) {
//                    GiftCertificateDTO giftCertificateDTO = new GiftCertificateDTO();
//                    long gcId;
//                    do {
//                        gcId = (long) (Math.random() * 10000 + 1);
//                    } while (!giftCertificateIds.add(gcId) || gcId < 1 || gcId > 10000);
//                    giftCertificateDTO.setId(gcId);
//                    giftCertificateDTOS.add(giftCertificateDTO);
//                }
//
//                orderDTO.setGiftCertificateDTOs(giftCertificateDTOS);
//                orderDTO.setUserDTO(userDTO);
//                orderService.save(orderDTO);
//            }
//        }
//
//    }

//    /*
//      filling tag_has_gift_certificate table script
//     */
//    public void fillTagHasGiftCertificateTable() {
//        for (int i = 0; i < 10000; i++) {
//            Set<Integer> tagIdSet = new HashSet<>();
//            int numOfTags = (int) (Math.random() * 5 + 1);
//            for (int j = 0; j < numOfTags; j++) {
//                Query query = sessionFactory.getCurrentSession().createSQLQuery(
//                        "INSERT INTO module3.tag_has_gift_certificate " +
//                                "(tag_id, gift_certificate_id) " +
//                                "VALUES (:tagId, :gcId)"
//                );
//                int tagId;
//                do {
//                    tagId = (int) (Math.random() * 1000 + 1);
//                } while (!tagIdSet.add(tagId) || tagId < 1 || tagId > 1000);
//                query.setParameter("tagId", tagId);
//                query.setParameter("gcId", i);
//                query.executeUpdate();
//            }
//        }
//    }
//
//    /*
//      filling gift_certificate table script
//     */
//    public void fillGiftCertificateTable() {
//        for (int i = 0; i < 10000; i++) {
//            GiftCertificate giftCertificate = new GiftCertificate();
//            giftCertificate.setName("giftCertificate" + i);
//            giftCertificate.setDescription("certificate " + "#" + i + " for " + (int) (Math.random() * 10 + 1) + " gift(s)");
//            giftCertificate.setPrice(new BigDecimal(String.format("%s.%s", (int) (Math.random() * 20 + 1), (int) (Math.random() * 20 + 1))));
//            giftCertificate.setCreateDate(LocalDateTime.now());
//            giftCertificate.setCreateDateTimeZone(ZonedDateTime.now().getOffset().toString());
//            giftCertificate.setLastUpdateDate(LocalDateTime.now());
//            giftCertificate.setLastUpdateDateTimeZone(ZonedDateTime.now().getOffset().toString());
//            giftCertificate.setDurationInDays((int) (Math.random() * 30 + 1));
//            sessionFactory.getCurrentSession().save(giftCertificate);
//        }
//    }
//
//    /*
//      filling tag table script
//     */
//    public void fillTagTable() {
//        for (int i = 0; i < 1000; i++) {
//            GiftCertificate giftCertificate = new GiftCertificate();
//            giftCertificate.setName("giftCertificate" + i);
//            giftCertificate.setDescription("certificate " + "#" + i + " for " + (int) (Math.random() * 10 + 1) + " gift(s)");
//            giftCertificate.setPrice(new BigDecimal(String.format("%s.%s", (int) (Math.random() * 20 + 1), (int) (Math.random() * 20 + 1))));
//            giftCertificate.setCreateDate(LocalDateTime.now());
//            giftCertificate.setCreateDateTimeZone(ZonedDateTime.now().getOffset().toString());
//            giftCertificate.setLastUpdateDate(LocalDateTime.now());
//            giftCertificate.setLastUpdateDateTimeZone(ZonedDateTime.now().getOffset().toString());
//            giftCertificate.setDurationInDays((int) (Math.random() * 30 + 1));
//            System.out.println(giftCertificate);
//        }
//    }
}
