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
@Table(name = "inquiry_category")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class inquiry_category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inquiry_category_no", nullable = false)
    private Long inquiryCategoryNo;

    @Column(name = "name", length = 50, nullable = false)
    private String name;
}
