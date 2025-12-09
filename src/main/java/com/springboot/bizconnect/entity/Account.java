package com.springboot.bizconnect.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Setter
@Getter
@Builder
@Table(name = "account")
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @EmbeddedId
    private AccountNo no;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("companyNo")
    @JoinColumn(name = "company_no")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("corporateAccountNo")
    @JoinColumn(name = "corporate_account_no")
    private CorporateAccount corporateAccount;

    @Embeddable
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class AccountNo implements Serializable {
        private Long companyNo;
        private Long corporateAccountNo;
    }

}
