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
@Table(name = "registration")
@AllArgsConstructor
@NoArgsConstructor
public class Registration {
    @EmbeddedId
    private registrationNo no;

    public static class registrationNo implements Serializable {
        @Column(name = "company_no", nullable = false)
        private Integer companyNo;

        @Column(name = "business_registration_no", nullable = false)
        private Integer businessRegistrationNo;
    }
}
