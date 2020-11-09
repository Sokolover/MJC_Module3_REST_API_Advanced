package com.epam.esm.sokolov.repository;

import com.epam.esm.sokolov.model.Order;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class OrderRepository {

    @PersistenceContext
    EntityManager entityManager;

    private SessionFactory sessionFactory;

    public OrderRepository(EntityManagerFactory factory) {
        if(factory.unwrap(SessionFactory.class) == null){
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.sessionFactory = factory.unwrap(SessionFactory.class);
    }

    public List<Order> findAllByUserAccountId(Long id, Long size, Long page) {
        return sessionFactory.getCurrentSession()
                .createNativeQuery("SELECT * " +
                        "FROM user_order " +
                        "WHERE user_order.user_account_id = ? " +
                        "ORDER BY user_order.id " +
                        "LIMIT ? " +
                        "OFFSET ? ", Order.class)
                .setParameter(1, id)
                .setParameter(2, size)
                .setParameter(3, page)
                .list();
    }

    public Optional<Order> findOneOrderByUserIdAndOrderId(Long userId, Long orderId) {
        return Optional.ofNullable(sessionFactory.getCurrentSession()
                .createNativeQuery("SELECT * " +
                        "FROM user_order " +
                        "WHERE user_order.user_account_id = ?1 " +
                        "AND user_order.id = ?2", Order.class)
                .setParameter(1, userId)
                .setParameter(2, orderId)
                .getSingleResult());
    }

    public List<Order> findAll() {
        return sessionFactory.getCurrentSession()
                .createNativeQuery("SELECT * FROM user_order", Order.class)
                .list();
    }

    public Order save(Order order) {
        sessionFactory.getCurrentSession()
                .saveOrUpdate(order);
        return order;
    }
}
