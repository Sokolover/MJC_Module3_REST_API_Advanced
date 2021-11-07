package com.epam.esm.sokolov.repository.order;

import com.epam.esm.sokolov.exception.RepositoryException;
import com.epam.esm.sokolov.model.Order;
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
public class OrderRepositoryImpl implements OrderRepository {

    private final EntityManagerFactory entityManagerFactory;

    @Override
    public List<Order> findAllByUserId(Long id, Long size, Long page) {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            return session.createNativeQuery(
                    "SELECT * " +
                            "FROM user_order " +
                            "WHERE user_order.user_account_id = ? " +
                            "ORDER BY user_order.id " +
                            "LIMIT ? " +
                            "OFFSET ? ", Order.class)
                    .setParameter(1, id)
                    .setParameter(2, size)
                    .setParameter(3, page)
                    .getResultList();
        }
    }

    @Override
    public Optional<Order> findOrderByUserIdAndId(Long userId, Long orderId) {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            return Optional.ofNullable(session.createNativeQuery(
                    "SELECT * " +
                            "FROM user_order " +
                            "WHERE user_order.user_account_id = ?1 " +
                            "AND user_order.id = ?2", Order.class)
                    .setParameter(1, userId)
                    .setParameter(2, orderId)
                    .getSingleResult());
        } catch (NoResultException e) {
            String message = format("Resource not found (userId = %s, orderId = %s)", userId, orderId);
            throw new RepositoryException(message, HttpStatus.NOT_FOUND, this.getClass());
        }
    }

    @Override
    public Order save(Order order) {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(order);
            transaction.commit();
            return order;
        }
    }

    @Override
    public Long countOrderByUserId(Long id) {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            BigInteger result = (BigInteger) session.createNativeQuery(
                    "SELECT COUNT(*) FROM user_order WHERE user_order.user_account_id = ?")
                    .setParameter(1, id)
                    .getSingleResult();
            return result.longValue();
        }
    }
}
