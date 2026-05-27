package com.springboot.bizconnect.domain.order.supplier.repository;

import com.springboot.bizconnect.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SupplierOrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findBySupplierCompany_CompanyNo(Long companyNo, Pageable pageable);

}
