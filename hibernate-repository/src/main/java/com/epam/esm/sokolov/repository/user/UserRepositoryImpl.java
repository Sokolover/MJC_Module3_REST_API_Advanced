package com.epam.esm.sokolov.repository.user;

import com.epam.esm.sokolov.model.user.User;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
//@Transactional
public class UserRepositoryImpl implements UserRepository {

    private final SessionFactory sessionFactory;

    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(sessionFactory.getCurrentSession()
                .createNativeQuery("SELECT * FROM user_account where user_account.username = :username",
                        User.class)
                .setParameter("username", username)
                .getSingleResult());
    }

    @Override
    public List<User> findAll(Long pageSize, Long pageNumber) {
        return sessionFactory.getCurrentSession().createNativeQuery(
                "SELECT * " +
                        "FROM user_account " +
                        "ORDER BY user_account.id " +
                        "LIMIT ? " +
                        "OFFSET ? ", User.class
        ).setParameter(1, pageSize)
                .setParameter(2, pageNumber)
                .getResultList();
    }

    @Override
    public Long findUserAmount() {
        BigInteger result = (BigInteger) sessionFactory.getCurrentSession().createNativeQuery(
                "SELECT COUNT(*) FROM user_account"
        ).getSingleResult();
        return result.longValue();
    }
}
