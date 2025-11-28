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
@Setter
@Getter
@Builder
@Table(name = "account")
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @EmbeddedId
    private accountNo no;

    public static class accountNo implements Serializable {
        @Column(name = "company_no", nullable = false)
        private Integer companyNo;

        @Column(name = "corporate_account_no", nullable = false)
        private Integer corporateAccountNo;
    }

}
