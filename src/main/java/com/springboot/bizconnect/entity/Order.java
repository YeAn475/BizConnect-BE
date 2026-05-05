package com.springboot.bizconnect.entity;

import com.springboot.bizconnect.enums.orderStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "`order`")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_no", nullable = false)
    private Long orderNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_company_no", nullable = false)
    private Company supplierCompany;  // 주문 받는 회사 (공급사)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_company_no", nullable = false)
    private Company buyerCompany;  // 주문 넣는 회사 (구매사)

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private orderStatus status;
}
