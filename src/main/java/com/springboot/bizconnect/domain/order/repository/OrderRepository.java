package com.springboot.bizconnect.domain.order.repository;

import org.springframework.data.domain.Page;
import com.springboot.bizconnect.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByBuyerCompany_CompanyNo(Long companyNo, Pageable pageable);
}
