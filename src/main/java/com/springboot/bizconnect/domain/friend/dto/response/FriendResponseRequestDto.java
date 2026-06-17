package com.springboot.bizconnect.domain.friend.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendResponseRequestDto {
    private Long requestNo;
    private String status;  // ACCEPTED 또는 REJECTED
}