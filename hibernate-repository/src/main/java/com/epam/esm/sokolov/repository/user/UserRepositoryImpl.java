package com.epam.esm.sokolov.repository.user;

import com.epam.esm.sokolov.exception.RepositoryException;
import com.epam.esm.sokolov.model.user.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Repository
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserRepositoryImpl implements UserRepository {

    private final EntityManagerFactory entityManagerFactory;

    @Override
    @Transactional
    public Optional<User> findUserByUsername(String username) {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            return Optional.ofNullable(session.createNativeQuery(
                    "SELECT * FROM user_account where user_account.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult());
        }
    }

    @Override
    public List<User> findAll(Long pageSize, Long pageNumber) {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            return session.createNativeQuery(
                    "SELECT * " +
                            "FROM user_account " +
                            "ORDER BY user_account.id " +
                            "LIMIT ? " +
                            "OFFSET ? ", User.class)
                    .setParameter(1, pageSize)
                    .setParameter(2, pageNumber)
                    .getResultList();
        }
    }

    @Override
    @Transactional
    public User save(User user) {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(user);
            transaction.commit();
            return user;
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            return Optional.ofNullable(session.createNativeQuery(
                    "SELECT * FROM user_account WHERE user_account.id = :id", User.class)
                    .setParameter("id", id)
                    .getSingleResult());
        } catch (NoResultException e) {
            String message = format("Resource not found (id = %s)", id);
            throw new RepositoryException(message, HttpStatus.NOT_FOUND, this.getClass());
        }
    }

    @Override
    public Long count() {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
            Root<User> user = criteriaQuery.from(User.class);
            criteriaQuery.select(criteriaBuilder.count(user));
            return session.createQuery(criteriaQuery).getSingleResult();
        }
    }
}
