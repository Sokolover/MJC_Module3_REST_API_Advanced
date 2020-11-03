package com.epam.esm.sokolov.repository;

import com.epam.esm.sokolov.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
