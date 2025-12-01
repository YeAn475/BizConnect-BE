package com.springboot.bizconnect.domain.alarm.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAlarmResponseDto {
//    private Long alarmNo;
//    private String title;
//    private String content;
//    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;
//    private Boolean isRead;
    private String message;
}
