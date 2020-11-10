package com.epam.esm.sokolov.repository;

import com.epam.esm.sokolov.model.GiftCertificate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Repository
@Transactional
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<GiftCertificate> findAllByTagsName(List<String> tagNamesCondition) {
        List list = entityManager.createNativeQuery(
                "select distinct gift_certificate.*\n" +
                        "from gift_certificate\n" +
                        "         inner join tag_has_gift_certificate\n" +
                        "                    on gift_certificate.id = tag_has_gift_certificate.gift_certificate_id\n" +
                        "         inner join tag\n" +
                        "                    on tag.id = tag_has_gift_certificate.tag_id\n" +
                        "where tag.name in :tagNamesCondition " +
                        "order by gift_certificate.id;",
                GiftCertificate.class
        ).setParameter("tagNamesCondition", tagNamesCondition)
                .getResultList();
        list.stream()
                .filter(GiftCertificate.class::isInstance)
                .map(GiftCertificate.class::cast)
                .collect(toList());
        return list;
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        return Optional.ofNullable(entityManager
                .createNamedQuery("findById", GiftCertificate.class)
                .setParameter("id", id)
                .getSingleResult());
    }

    @Override
    public GiftCertificate save(GiftCertificate giftCertificate) {
        entityManager.persist(giftCertificate);
        return giftCertificate;
    }

    @Override
    public List<GiftCertificate> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> rootEntry = criteriaQuery.from(GiftCertificate.class);
        CriteriaQuery<GiftCertificate> all = criteriaQuery.select(rootEntry);
        TypedQuery<GiftCertificate> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    @Override
    public List<GiftCertificate> findAllById(List<Long> ids) {
        return entityManager.createNativeQuery(
                "SELECT * FROM gift_certificate WHERE gift_certificate.id IN :ids", GiftCertificate.class)
                .setParameter("ids", ids)
                .getResultList();
    }
}
