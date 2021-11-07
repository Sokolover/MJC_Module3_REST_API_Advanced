package com.epam.esm.sokolov.repository.certificate;

import com.epam.esm.sokolov.exception.RepositoryException;
import com.epam.esm.sokolov.model.GiftCertificate;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Repository
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {

    private final EntityManagerFactory entityManagerFactory;

    @Override
    public List<GiftCertificate> findByTagNames(List<String> tagNamesCondition, Long pageSize, Long pageOffsetInQuery) {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            return session.createNativeQuery(
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
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            return Optional.ofNullable(session.createNativeQuery(
                    "SELECT * FROM gift_certificate WHERE gift_certificate.id = :id", GiftCertificate.class)
                    .setParameter("id", id)
                    .getSingleResult());
        } catch (NoResultException e) {
            String message = format("Resource not found (id = %s)", id);
            throw new RepositoryException(message, HttpStatus.NOT_FOUND, this.getClass());
        }
    }

    @Override
    public GiftCertificate save(GiftCertificate giftCertificate) {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(giftCertificate);
            transaction.commit();
            return giftCertificate;
        }
    }

    @Override
    public List<GiftCertificate> findAllByIdIn(List<Long> ids) {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            return session.createNativeQuery(
                    "SELECT * FROM gift_certificate WHERE gift_certificate.id IN :ids", GiftCertificate.class)
                    .setParameter("ids", ids)
                    .getResultList();
        }
    }

    @Override
    public Long countGiftCertificatesByTagsNameIn(List<String> tagNamesCondition) {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            BigInteger result = (BigInteger) session.createNativeQuery(
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
}
