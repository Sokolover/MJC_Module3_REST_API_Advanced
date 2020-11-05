package com.epam.esm.sokolov.repository;

import com.epam.esm.sokolov.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT * FROM user_order WHERE user_order.user_account_id = ?1", nativeQuery = true)
    List<Order> findAllByUserAccountId(Long id);

    @Query(value = "SELECT * FROM user_order WHERE user_account_id = ?1 AND user_order.id = ?2", nativeQuery = true)
    Optional<Order> findOneOrderByUserIdAndOrderId(Long userId, Long orderId);

}
