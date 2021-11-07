package com.epam.esm.sokolov.repository.tag;

import com.epam.esm.sokolov.model.Tag;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import java.util.Optional;

@Repository
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TagRepositoryImpl implements TagRepository {

    private final EntityManagerFactory entityManagerFactory;

    public Optional<Tag> findTheMostWidelyUsedTag() {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            return Optional.ofNullable(session.createNativeQuery(
                    "select tag.*\n" +
                            "from tag,\n" +
                            "     tag_has_gift_certificate,\n" +
                            "     (select gift_certificate.id\n" +
                            "      from gift_certificate,\n" +
                            "           user_order_has_gift_certificate,\n" +
                            "           (select *\n" +
                            "            from user_order\n" +
                            "            where user_order.user_account_id =\n" +
                            "                  (select user_order.user_account_id\n" +
                            "                   from user_order\n" +
                            "                   group by user_order.user_account_id\n" +
                            "                   order by sum(user_order.cost) desc\n" +
                            "                   limit 1)) as max_sum_orders\n" +
                            "      where max_sum_orders.id = user_order_has_gift_certificate.user_order_id\n" +
                            "        and gift_certificate.id = user_order_has_gift_certificate.gift_certificate_id) as max_sum_certificates_ids\n" +
                            "where tag.id = tag_has_gift_certificate.tag_id\n" +
                            "  and max_sum_certificates_ids.id = tag_has_gift_certificate.gift_certificate_id\n" +
                            "group by tag.name\n" +
                            "order by count(tag.name) desc,\n" +
                            "         tag.name \n" +
                            "limit 1;", Tag.class
            ).getSingleResult());
        }
    }
}
