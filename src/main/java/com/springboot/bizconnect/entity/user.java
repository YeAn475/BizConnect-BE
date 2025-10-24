package com.springboot.bizconnect.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class user extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no", nullable = false)
    private Integer userNo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_no", nullable = false)
    private role role;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_no", nullable = false)
    private position position;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_status_no", nullable = false)
    private user_status userStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_no", nullable = false)
    private company company;

    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "phone_number", length = 30, nullable = false)
    private String phoneNumber;

    @Column(name = "address", length = 100, nullable = false)
    private String address;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "image_url", length = 255, nullable = true)
    private String imageUrl;

    @Column(name = "is_open", nullable = false)
    private Boolean isOpen;

    @OneToMany(mappedBy = "user")
    private List<alarm> notices = new ArrayList<>();

}
