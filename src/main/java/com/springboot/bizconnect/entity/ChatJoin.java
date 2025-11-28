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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "chat_join")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatJoin {
    @EmbeddedId
    private chatJoinNo no;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userNo")
    @JoinColumn(name = "user_no")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("chatroomNo")
    @JoinColumn(name = "chatroom_no")
    private Chatroom chatroom;

    @Embeddable
    public static class chatJoinNo implements Serializable {
        @Column(name = "user_no", nullable = false)
        private Integer userNo;

        @Column(name = "chatroom_no", nullable = false)
        private Integer chatroomNo;
    }

}
