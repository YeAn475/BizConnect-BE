package com.springboot.bizconnect.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "user_alarm")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAlarm {
    @EmbeddedId
    private userAlarmNo no;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userNo")
    @JoinColumn(name = "user_no")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("alarmNo")
    @JoinColumn(name = "alarm_no")
    private Alarm alarm;

    @Column(name = "is_read", nullable = false)
    private Boolean isRead = Boolean.FALSE;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = Boolean.FALSE;

    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class userAlarmNo implements Serializable {
        private Long userNo;
        private Long alarmNo;
    }

}
