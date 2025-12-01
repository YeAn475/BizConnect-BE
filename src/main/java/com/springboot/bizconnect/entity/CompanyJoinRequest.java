package com.springboot.bizconnect.entity;

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
@Table(name = "company_join_request")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyJoinRequest extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_join_request_no", nullable = false)
    private Long companyRequestNo;


    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private requestStatus status;

    @OneToOne
    @JoinColumn(name = "user_no", nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "company_no", nullable = false)
    private Company company;

}
