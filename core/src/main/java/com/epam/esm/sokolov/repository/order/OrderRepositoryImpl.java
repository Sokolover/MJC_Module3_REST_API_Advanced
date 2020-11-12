package com.epam.esm.sokolov.repository.order;

import com.epam.esm.sokolov.exception.RepositoryException;
import com.epam.esm.sokolov.model.Order;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Repository
@Transactional
public class OrderRepositoryImpl implements OrderRepository {

//    @PersistenceContext
//    EntityManager entityManager;
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Order> findAllByUserAccountId(Long id, Long size, Long page) {

        return sessionFactory.getCurrentSession().createNativeQuery(
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

//        return entityManager.createNativeQuery(
//                "SELECT * " +
//                        "FROM user_order " +
//                        "WHERE user_order.user_account_id = ? " +
//                        "ORDER BY user_order.id " +
//                        "LIMIT ? " +
//                        "OFFSET ? ", Order.class)
//                .setParameter(1, id)
//                .setParameter(2, size)
//                .setParameter(3, page)
//                .getResultList();
    }

    @Override
    public Optional<Order> findOneOrderByUserIdAndOrderId(Long userId, Long orderId) {
        try {
            return Optional.ofNullable(sessionFactory.getCurrentSession().createNativeQuery(
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


//        try {
//            return Optional.ofNullable((Order) entityManager.createNativeQuery(
//                    "SELECT * " +
//                            "FROM user_order " +
//                            "WHERE user_order.user_account_id = ?1 " +
//                            "AND user_order.id = ?2", Order.class)
//                    .setParameter(1, userId)
//                    .setParameter(2, orderId)
//                    .getSingleResult());
//        } catch (NoResultException e) {
//            String message = format("Resource not found (userId = %s, orderId = %s)", userId, orderId);
//            throw new RepositoryException(message, HttpStatus.NOT_FOUND, this.getClass());
//        }
    }

    @Override
    public Order save(Order order) {
        sessionFactory.getCurrentSession().saveOrUpdate(order);
        return order;
//        entityManager.persist(order);
//        return order;
    }

    @Override
    public Long findOrderAmountByUserId(Long id) {
        BigInteger result = (BigInteger) sessionFactory.getCurrentSession().createNativeQuery(
                "SELECT COUNT(*) FROM user_order WHERE user_order.user_account_id = ?")
                .setParameter(1, id)
                .getSingleResult();
        return result.longValue();

//        BigInteger result = (BigInteger) entityManager.createNativeQuery(
//                "SELECT COUNT(*) FROM user_order WHERE user_order.user_account_id = ?")
//                .setParameter(1, id)
//                .getSingleResult();
    }


}
