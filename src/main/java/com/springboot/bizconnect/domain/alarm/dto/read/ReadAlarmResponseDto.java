package com.springboot.bizconnect.domain.alarm.dto.read;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ReadAlarmResponseDto {
    private Long alarmNo;
    private String title;
    private String content;
}
