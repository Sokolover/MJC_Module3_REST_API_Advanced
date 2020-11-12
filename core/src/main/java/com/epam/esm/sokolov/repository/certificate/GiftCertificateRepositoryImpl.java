package com.epam.esm.sokolov.repository.certificate;

import com.epam.esm.sokolov.exception.RepositoryException;
import com.epam.esm.sokolov.model.GiftCertificate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Repository
@Transactional
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {

    //    @PersistenceContext
//    EntityManager entityManager;
    @Autowired
    private SessionFactory sessionFactory;

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

//        return entityManager.createNativeQuery(
//                "select distinct gift_certificate.*\n" +
//                        "from gift_certificate\n" +
//                        "         inner join tag_has_gift_certificate\n" +
//                        "                    on gift_certificate.id = tag_has_gift_certificate.gift_certificate_id\n" +
//                        "         inner join tag\n" +
//                        "                    on tag.id = tag_has_gift_certificate.tag_id\n" +
//                        "where tag.name in :tagNamesCondition " +
//                        "order by gift_certificate.id " +
//                        "limit :pageSize " +
//                        "offset :pageOffsetInQuery ",
//                GiftCertificate.class
//        ).setParameter("tagNamesCondition", tagNamesCondition)
//                .setParameter("pageSize", pageSize)
//                .setParameter("pageOffsetInQuery", pageOffsetInQuery)
//                .getResultList();
    }

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
//        try {
//            return Optional.ofNullable((GiftCertificate) entityManager
//                    .createNativeQuery("SELECT * FROM gift_certificate WHERE gift_certificate.id = :id", GiftCertificate.class)
//                    .setParameter("id", id)
//                    .getSingleResult());
//        } catch (NoResultException e) {
//            String message = format("Resource not found (id = %s)", id);
//            throw new RepositoryException(message, HttpStatus.NOT_FOUND, this.getClass());
//        }
    }

    @Override
    public GiftCertificate save(GiftCertificate giftCertificate) {
        sessionFactory.getCurrentSession().saveOrUpdate(giftCertificate);
        return giftCertificate;
//        entityManager.persist(giftCertificate);
//        return giftCertificate;
    }

    @Override
    public List<GiftCertificate> findAllById(List<Long> ids) {
        return sessionFactory.getCurrentSession().createNativeQuery(
                "SELECT * FROM gift_certificate WHERE gift_certificate.id IN :ids", GiftCertificate.class)
                .setParameter("ids", ids)
                .getResultList();

//        return entityManager.createNativeQuery(
//                "SELECT * FROM gift_certificate WHERE gift_certificate.id IN :ids", GiftCertificate.class)
//                .setParameter("ids", ids)
//                .getResultList();
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
//        BigInteger result = (BigInteger) entityManager.createNativeQuery(
//                "select distinct count(*)\n" +
//                        "from gift_certificate\n" +
//                        "         inner join tag_has_gift_certificate\n" +
//                        "                    on gift_certificate.id = tag_has_gift_certificate.gift_certificate_id\n" +
//                        "         inner join tag\n" +
//                        "                    on tag.id = tag_has_gift_certificate.tag_id\n" +
//                        "where tag.name in :tagNamesCondition ")
//                .setParameter("tagNamesCondition", tagNamesCondition)
//                .getSingleResult();
//        return result.longValue();
    }
}