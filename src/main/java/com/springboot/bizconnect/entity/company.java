package com.springboot.bizconnect.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "company")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class company extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_no", nullable = false)
    private Long companyNo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "affiliation_no", nullable = false)
    private affiliation affiliation;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_no", nullable = false)
    private branch branch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_registration_no", nullable = false)
    private business_registration business_registration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "corporate_account_no", nullable = false)
    private corporate_account corporate_account;

    @Column(name = "phone_number", length = 30, nullable = false)
    private String phoneNumber;

    @Column(name = "address", length = 100, nullable = false)
    private String address;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = Boolean.FALSE;
}
