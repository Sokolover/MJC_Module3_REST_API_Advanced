package com.epam.esm.sokolov.repository;

import com.epam.esm.sokolov.model.GiftCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long> {

    @Query(value = "select distinct gift_certificate.*\n" +
            "from gift_certificate\n" +
            "         inner join tag_has_gift_certificate\n" +
            "                    on gift_certificate.id = tag_has_gift_certificate.gift_certificate_id\n" +
            "         inner join tag\n" +
            "                    on tag.id = tag_has_gift_certificate.tag_id\n" +
            "where tag.name in :tagNamesCondition " +
            "order by gift_certificate.id;", nativeQuery = true)
    List<GiftCertificate> findAllByTagsName(@Param("tagNamesCondition") List<String> tagNamesCondition);
}
