package com.springboot.bizconnect.domain.order.buyer.repository;

import com.springboot.bizconnect.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BuyerOrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByBuyerCompany_CompanyNo(Long companyNo, Pageable pageable);
}
