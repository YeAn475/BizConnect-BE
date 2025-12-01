package com.springboot.bizconnect.domain.alarm.dto.create;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "제목")
    private String title;
    @Schema(description = "내용")
    private String content;
}
