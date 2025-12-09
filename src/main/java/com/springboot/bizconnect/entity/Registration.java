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
@Table(name = "registration")
@AllArgsConstructor
@NoArgsConstructor
public class Registration {
    @EmbeddedId
    private registrationNo no;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("companyNo")
    @JoinColumn(name = "company_no")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("businessRegistrationNo")
    @JoinColumn(name = "business_registration_no")
    private BusinessRegistration businessRegistration;

    @Embeddable
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class registrationNo implements Serializable {
        @Column(name = "company_no", nullable = false)
        private Long companyNo;

        @Column(name = "business_registration_no", nullable = false)
        private Long businessRegistrationNo;
    }
}
