package com.springboot.bizconnect.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "business_registration")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Business_registration extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "business_registration_no", nullable = false)
    private Long businessRegistrationNo;

    @Column(name = "number", length = 255, nullable = false)
    private String number;
}
