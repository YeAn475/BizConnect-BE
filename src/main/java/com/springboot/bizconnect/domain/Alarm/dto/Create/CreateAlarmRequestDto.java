package com.springboot.bizconnect.domain.Alarm.dto.Create;

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
public class CreateAlarmRequestDto {
    private String title;
    private String content;
}
