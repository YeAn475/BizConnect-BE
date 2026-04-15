package com.springboot.bizconnect.domain.order.repository;

import com.springboot.bizconnect.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
