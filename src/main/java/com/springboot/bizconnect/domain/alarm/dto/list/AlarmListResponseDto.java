package com.springboot.bizconnect.domain.alarm.dto.list;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class AlarmListResponseDto {
    private Long alarmNo;
    private String title;
    private String content;
}
