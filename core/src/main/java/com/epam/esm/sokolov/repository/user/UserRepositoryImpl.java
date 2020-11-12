package com.epam.esm.sokolov.repository.user;

import com.epam.esm.sokolov.model.User;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

//    @PersistenceContext
//    EntityManager entityManager;
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<User> findAll(Long pageSize, Long pageNumber) {

        return sessionFactory.getCurrentSession().createNativeQuery(
                "SELECT * " +
                        "FROM user_account " +
                        "ORDER BY user_account.id " +
                        "LIMIT ? " +
                        "OFFSET ? ", User.class
        )                .setParameter(1, pageSize)
                .setParameter(2, pageNumber)
                .getResultList();

//        Query query = entityManager.createNativeQuery(
//                "SELECT * " +
//                        "FROM user_account " +
//                        "ORDER BY user_account.id " +
//                        "LIMIT ? " +
//                        "OFFSET ? ", User.class)
//                .setParameter(1, pageSize)
//                .setParameter(2, pageNumber);
//        return query.getResultList();
    }

    @Override
    public Long findUserAmount() {

        BigInteger result =  (BigInteger) sessionFactory.getCurrentSession().createNativeQuery(
                "SELECT COUNT(*) FROM user_account"
        ).getSingleResult();
        return result.longValue();

//        BigInteger result = (BigInteger) entityManager.createNativeQuery(
//                "SELECT COUNT(*) FROM user_account")
//                .getSingleResult();
//        return result.longValue();
    }
}
