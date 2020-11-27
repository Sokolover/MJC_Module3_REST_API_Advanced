package com.epam.esm.sokolov.repository.certificate;

import com.epam.esm.sokolov.exception.RepositoryException;
import com.epam.esm.sokolov.model.GiftCertificate;
import org.hibernate.SessionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Repository
// todo !!! check it: if in method there is a single SQL-query, then we don't need @Transactional
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {

    private final SessionFactory sessionFactory;

    public GiftCertificateRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<GiftCertificate> findByTagsNames(List<String> tagNamesCondition, Long pageSize, Long pageOffsetInQuery) {
        return sessionFactory.getCurrentSession().createNativeQuery(
                "SELECT DISTINCT gift_certificate.*\n" +
                        "FROM gift_certificate\n" +
                        "         INNER JOIN tag_has_gift_certificate\n" +
                        "                    ON gift_certificate.id = tag_has_gift_certificate.gift_certificate_id\n" +
                        "         INNER JOIN tag\n" +
                        "                    ON tag.id = tag_has_gift_certificate.tag_id\n" +
                        "WHERE tag.name IN :tagNamesCondition " +
                        "ORDER BY gift_certificate.id " +
                        "LIMIT :pageSize " +
                        "OFFSET :pageOffsetInQuery ",
                GiftCertificate.class
        ).setParameter("tagNamesCondition", tagNamesCondition)
                .setParameter("pageSize", pageSize)
                .setParameter("pageOffsetInQuery", pageOffsetInQuery)
                .getResultList();
    }

    /*
    todo
     read about
     - merge and persist methods difference
     - what hibernate rather than jpa
     - how to migrate states: detach -> persist
     - caching HTTP requests
     - body in GET and DELETE HTTP-methods
     - TRACE HTTP-method
    */

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        try {
            return Optional.ofNullable(sessionFactory.getCurrentSession()
                    .createNativeQuery(
                            "SELECT * FROM gift_certificate WHERE gift_certificate.id = :id", GiftCertificate.class)
                    .setParameter("id", id)
                    .getSingleResult());
        } catch (NoResultException e) {
            String message = format("Resource not found (id = %s)", id);
            throw new RepositoryException(message, HttpStatus.NOT_FOUND, this.getClass());
        }
    }

    @Override
    @Transactional
    public GiftCertificate save(GiftCertificate giftCertificate) {
        sessionFactory.getCurrentSession().saveOrUpdate(giftCertificate);
        return giftCertificate;
    }

    @Override
    public List<GiftCertificate> findAllById(List<Long> ids) {
        return sessionFactory.getCurrentSession().createNativeQuery(
                "SELECT * FROM gift_certificate WHERE gift_certificate.id IN :ids", GiftCertificate.class)
                .setParameter("ids", ids)
                .getResultList();
    }

    @Override
    public Long findGiftCertificateAmountByTagNames(List<String> tagNamesCondition) {
        BigInteger result = (BigInteger) sessionFactory.getCurrentSession().createNativeQuery(
                "SELECT DISTINCT COUNT(*)\n" +
                        "FROM gift_certificate\n" +
                        "         INNER JOIN tag_has_gift_certificate\n" +
                        "                    ON gift_certificate.id = tag_has_gift_certificate.gift_certificate_id\n" +
                        "         INNER JOIN tag\n" +
                        "                    ON tag.id = tag_has_gift_certificate.tag_id\n" +
                        "WHERE tag.name IN :tagNamesCondition ")
                .setParameter("tagNamesCondition", tagNamesCondition)
                .getSingleResult();
        return result.longValue();
    }
}
