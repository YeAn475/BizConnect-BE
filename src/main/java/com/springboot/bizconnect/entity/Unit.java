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
@Table(name = "unit")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unit_no", nullable = false)
    private Integer unitNo;

    @Column(name = "name", length = 100, nullable = false)
    private String name;
}
