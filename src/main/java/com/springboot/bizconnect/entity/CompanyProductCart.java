package com.springboot.bizconnect.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "company_product_cart")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyProductCart extends BaseEntity {
    @EmbeddedId
    private CompanyProductNo no;
    
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId("companyNo")
    @JoinColumn(name = "company_no")
    private Company company;
    
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId("productNo")
    @JoinColumn(name = "product_no")
    private Product product;

    @Column(name = "is_used", nullable = false)
    private Boolean isUsed = Boolean.TRUE;

    @Embeddable
    @Getter 
    @NoArgsConstructor 
    @AllArgsConstructor
    @EqualsAndHashCode // 복합키 비교를 위한 어노테이
    public static class CompanyProductNo implements Serializable {
        @Column(name = "company_no", nullable = false)
        private Long companyNo;

        @Column(name = "product_no", nullable = false)
        private Long productNo;

    }
}
