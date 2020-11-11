package com.epam.esm.sokolov.repository.user;

import com.epam.esm.sokolov.model.User;
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

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<User> findAll(Long pageSize, Long pageNumber) {
        Query query = entityManager.createNativeQuery(
                "SELECT * " +
                        "FROM user_account " +
                        "ORDER BY user_account.id " +
                        "LIMIT ? " +
                        "OFFSET ? ", User.class)
                .setParameter(1, pageSize)
                .setParameter(2, pageNumber);
        return query.getResultList();
    }

    @Override
    public Long findUserAmount() {
        BigInteger result = (BigInteger) entityManager.createNativeQuery(
                "SELECT COUNT(*) FROM user_account")
                .getSingleResult();
        return result.longValue();
    }
}
