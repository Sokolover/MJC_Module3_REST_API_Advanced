package com.epam.esm.sokolov.repository.order;

import com.epam.esm.sokolov.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT * " +
            "FROM user_order " +
            "WHERE user_order.user_account_id = ?1 " +
            "ORDER BY user_order.id " +
            "LIMIT ?2 " +
            "OFFSET ?3 ", nativeQuery = true)
    List<Order> findAllByUserId(Long id, Long size, Long page);

    Optional<Order> findOrderByUserIdAndId(Long userId, Long orderId);

    Long countOrderByUserId(Long id);
}
