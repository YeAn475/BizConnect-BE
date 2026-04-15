package com.springboot.bizconnect.domain.alarm.controller;

import com.springboot.bizconnect.domain.alarm.dto.create.CreateAlarmRequestDto;
import com.springboot.bizconnect.domain.alarm.dto.create.CreateAlarmResponseDto;
import com.springboot.bizconnect.domain.alarm.dto.list.AlarmListRequestDto;
import com.springboot.bizconnect.domain.alarm.dto.list.AlarmListResponseDto;
import com.springboot.bizconnect.domain.alarm.dto.read.ReadAlarmResponseDto;
import com.springboot.bizconnect.domain.alarm.service.AlarmService;
import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/alarm")
@Tag(name = "Alarm", description = "알람 관련 API")
public class AlarmController {

    private final AlarmService alarmService;

    @PostMapping("/create")
    @Operation(summary = "알림 생성", description = "유저가 알림을 생성합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreateAlarmResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "NOT FOUND",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "INTERNAL SERVER ERROR",
                    content = @Content(mediaType = "application/json")
            )
    })
    public ResponseEntity<CreateAlarmResponseDto> createAlarm(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ParameterObject CreateAlarmRequestDto requestDto
    ) {
        CreateAlarmResponseDto responseDto = alarmService.createAlarm(userDetails, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/list") // get요청에 @RequestBody 사용 못함
    @Operation(summary = "알림 리스트", description = "유저가 알림 리스트를 확인합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AlarmListResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "NOT FOUND",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "INTERNAL SERVER ERROR",
                    content = @Content(mediaType = "application/json")
            )
    })
    public ResponseEntity<List<AlarmListResponseDto>> getAlarmList(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ParameterObject AlarmListRequestDto requestDto
    ) {
        List<AlarmListResponseDto> responseDtos = alarmService.getAlarmList(userDetails, requestDto);
        return ResponseEntity.ok(responseDtos);
    }
    // 알람 읽기
    @PatchMapping("/read")
    @Operation(summary = "알람을 확인합니다.", description = "유저가 알림을 확인합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ReadAlarmResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "NOT FOUND",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "INTERNAL SERVER ERROR",
                    content = @Content(mediaType = "application/json")
            )
    })
    public ResponseEntity<ReadAlarmResponseDto> readAlarm(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Long alarmNo) {
        ReadAlarmResponseDto responseDto = alarmService.readAlarm(userDetails, alarmNo);
        return ResponseEntity.ok(responseDto);
    }
    // 알람 삭제
    @PatchMapping("/delete")
    @Operation(summary = "알람 삭제", description = "알람을 삭제합니다.")
    public ResponseEntity<Map<String, String>> deleteAlarm(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Long alarmNo) {
        alarmService.deleteAlarm(userDetails, alarmNo);
        return ResponseEntity.ok(Map.of("message", "알람이 삭제되었습니다."));
    }
}
