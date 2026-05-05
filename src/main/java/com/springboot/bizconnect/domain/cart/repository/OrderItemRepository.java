package com.springboot.bizconnect.domain.cart.repository;

import com.springboot.bizconnect.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrder_OrderNo(Long orderNo);
    Optional<OrderItem> findByOrder_OrderNoAndProduct_ProductNo(Long orderNo, Long productNo);
}
