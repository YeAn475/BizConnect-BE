package com.springboot.bizconnect.entity;

import com.springboot.bizconnect.enums.friendStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "friend_request")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendRequest extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friend_request_no", nullable = false)
    private Long friendRequestNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_user_no", nullable = false)
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_user_no", nullable = false)
    private User receiver;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private friendStatus status;

}
