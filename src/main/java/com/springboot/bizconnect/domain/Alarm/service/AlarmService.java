package com.springboot.bizconnect.domain.Alarm.service;

import com.springboot.bizconnect.domain.Alarm.dto.Create.CreateAlarmRequestDto;
import com.springboot.bizconnect.domain.Alarm.dto.Create.CreateAlarmResponseDto;
import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.entity.User;

import java.util.List;

public interface AlarmService {
    CreateAlarmResponseDto createAlarm(CustomUserDetails userDetails, CreateAlarmRequestDto requestDto);
    // 특정 한명(친구 요청, 1:1 채팅, 개인 메시지)
    void sendToUser(User sender, Long receiverNo, String title, String content);
    // 운영관리자(회사 등록 요청, 시스템 문의, 신고 접수)
    void sendToOperationAdmins(User sender, String title, String content);
    // 특정회사 관리자들(회사 가입 신청, 회사 탈퇴 요청)
    void sendToCompanyAdmins(User sender, Long companyNo, String title, String content);
    // 특정회사전체 직원(발주 등록, 회사 공지사항, 일정 생성)
    void sendToCompanyMembers(User sender, Long companyNo, String title, String content);
    // 특정회사의 특정 지점 직원들(지점별 발주, 공지, 일정)
    void sendToBranchMembers(User sender, Long companyNo, Long branchNo, String title, String content);
    // 여러명(채팅방, 특정 그룹 지정 발송)
    void sendToUsers(User sender, List<Long> receiverNos, String title, String content);
}
