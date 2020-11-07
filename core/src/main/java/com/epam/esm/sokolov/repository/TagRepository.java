package com.epam.esm.sokolov.repository;

import com.epam.esm.sokolov.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {


    @Query(value = "select tag.*\n" +
            "from tag,\n" +
            "     tag_has_gift_certificate,\n" +
            "     (select gift_certificate.*\n" +
            "      from gift_certificate,\n" +
            "           user_order_has_gift_certificate,\n" +
            "           (select *\n" +
            "            from user_order\n" +
            "            where user_order.user_account_id =\n" +
            "                  (select user_acc_id_with_max_order_cost.user_account_id\n" +
            "                   from (select max_cost.user_account_id, max(sum_cost)\n" +
            "                         from (select user_order.user_account_id, sum(user_order.cost) as sum_cost\n" +
            "                               from user_order\n" +
            "                               group by user_order.user_account_id\n" +
            "                               order by sum_cost desc) as max_cost) as user_acc_id_with_max_order_cost)) as max_sum_orders\n" +
            "      where max_sum_orders.id = user_order_has_gift_certificate.user_order_id\n" +
            "        and gift_certificate.id = user_order_has_gift_certificate.gift_certificate_id) as max_sum_certificates\n" +
            "where tag.id = tag_has_gift_certificate.tag_id\n" +
            "  and max_sum_certificates.id = tag_has_gift_certificate.gift_certificate_id\n" +
            "GROUP BY tag.name\n" +
            "ORDER BY count(tag.name) DESC\n" +
            "LIMIT 1;", nativeQuery = true)
    Optional<Tag> findTheMostWidelyUsedTag();


}
