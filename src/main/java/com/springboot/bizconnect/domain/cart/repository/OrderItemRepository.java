package com.springboot.bizconnect.domain.cart.repository;

import com.springboot.bizconnect.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
