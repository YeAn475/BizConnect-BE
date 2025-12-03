package com.springboot.bizconnect.entity;

import com.springboot.bizconnect.enums.CompanyType;
import com.springboot.bizconnect.enums.requestStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "company_request")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyRequest extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_request_no", nullable = false)
    private Long companyRequestNo;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "affiliation_name", nullable = false, length = 100)
    private String affiliationName;

    @Column(name = "branch_name", nullable = false, length = 100)
    private String branchName;

    @Column(name = "address", nullable = false, length = 100)
    private String address;

    @Column(name = "phone_number", nullable = false, length = 30)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private CompanyType status;

    @OneToOne
    @JoinColumn(name = "user_no", nullable = false)
    private User user;

}
