package com.epam.esm.sokolov.repository;

import com.epam.esm.sokolov.model.GiftCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long> {

    @Query(value = "SELECT DISTINCT gift_certificate.*\n" +
            "FROM gift_certificate\n" +
            "         INNER JOIN tag_has_gift_certificate\n" +
            "                    ON gift_certificate.id = tag_has_gift_certificate.gift_certificate_id\n" +
            "         INNER JOIN tag\n" +
            "                    ON tag.id = tag_has_gift_certificate.tag_id\n" +
            "WHERE tag.name IN :tagNamesCondition " +
            "ORDER BY gift_certificate.id " +
            "LIMIT :pageSize " +
            "OFFSET :pageOffsetInQuery ", nativeQuery = true)
    List<GiftCertificate> findByTagNames(@Param("tagNamesCondition") List<String> tagNamesCondition,
                                         @Param("pageSize") Long pageSize,
                                         @Param("pageOffsetInQuery") Long pageOffsetInQuery);

    @Query(value = "SELECT * " +
            "FROM gift_certificate " +
            "WHERE gift_certificate.id IN :ids ", nativeQuery = true)
    List<GiftCertificate> findAllByIds(@Param("ids") List<Long> ids);

    @Query(value = "SELECT DISTINCT COUNT(*)\n" +
            "FROM gift_certificate\n" +
            "         INNER JOIN tag_has_gift_certificate\n" +
            "                    ON gift_certificate.id = tag_has_gift_certificate.gift_certificate_id\n" +
            "         INNER JOIN tag\n" +
            "                    ON tag.id = tag_has_gift_certificate.tag_id\n" +
            "WHERE tag.name IN :tagNamesCondition ", nativeQuery = true)
    Long countGiftCertificatesByTagNames(@Param("tagNamesCondition") List<String> tagNamesCondition);
}
