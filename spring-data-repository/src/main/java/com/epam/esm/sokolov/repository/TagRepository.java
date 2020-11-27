package com.epam.esm.sokolov.repository;

import com.epam.esm.sokolov.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query(value = "SELECT tag.*\n" +
            "FROM tag,\n" +
            "     tag_has_gift_certificate,\n" +
            "     (SELECT gift_certificate.id\n" +
            "      FROM gift_certificate,\n" +
            "           user_order_has_gift_certificate,\n" +
            "           (SELECT *\n" +
            "            FROM user_order\n" +
            "            WHERE user_order.user_account_id =\n" +
            "                  (SELECT user_order.user_account_id\n" +
            "                   FROM user_order\n" +
            "                   GROUP BY user_order.user_account_id\n" +
            "                   ORDER BY sum(user_order.cost) DESC \n" +
            "                   LIMIT 1)) AS max_sum_orders\n" +
            "      WHERE max_sum_orders.id = user_order_has_gift_certificate.user_order_id\n" +
            "        AND gift_certificate.id = user_order_has_gift_certificate.gift_certificate_id) AS max_sum_certificates_ids\n" +
            "WHERE tag.id = tag_has_gift_certificate.tag_id\n" +
            "  AND max_sum_certificates_ids.id = tag_has_gift_certificate.gift_certificate_id\n" +
            "GROUP BY tag.name\n" +
            "ORDER BY COUNT(tag.name) DESC ,\n" +
            "         tag.name \n" +
            "LIMIT 1 ", nativeQuery = true)
    Optional<Tag> findTheMostWidelyUsedTag();

}
