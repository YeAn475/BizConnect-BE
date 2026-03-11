package com.springboot.bizconnect.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
    private companyProductNo no;

    @Column(name = "is_used", nullable = false)
    private Boolean isUsed = Boolean.TRUE;

    public static class companyProductNo implements Serializable {
        @Column(name = "company_no", nullable = false)
        private Integer companyNo;

        @Column(name = "product_no", nullable = false)
        private Integer productNo;

    }
}
