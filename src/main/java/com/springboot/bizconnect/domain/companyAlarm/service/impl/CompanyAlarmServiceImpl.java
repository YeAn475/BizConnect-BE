package com.springboot.bizconnect.domain.companyAlarm.service.impl;

import com.springboot.bizconnect.domain.alarm.repository.AlarmRepository;
import com.springboot.bizconnect.domain.alarm.service.AlarmService;
import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.company.repository.CompanyRepository;
import com.springboot.bizconnect.domain.companyAlarm.dto.approve.CompanyAlarmApproveResponseDto;
import com.springboot.bizconnect.domain.companyAlarm.dto.reject.CompanyAlarmRejectResponseDto;
import com.springboot.bizconnect.domain.companyAlarm.repository.AffiliationRepository;
import com.springboot.bizconnect.domain.companyAlarm.repository.BranchRepository;
import com.springboot.bizconnect.domain.companyAlarm.repository.CompanyRequestRepository;
import com.springboot.bizconnect.domain.companyAlarm.service.CompanyAlarmService;
import com.springboot.bizconnect.domain.user.repository.UserRepository;
import com.springboot.bizconnect.entity.Affiliation;
import com.springboot.bizconnect.entity.Alarm;
import com.springboot.bizconnect.entity.Branch;
import com.springboot.bizconnect.entity.Company;
import com.springboot.bizconnect.entity.CompanyRequest;
import com.springboot.bizconnect.entity.User;
import com.springboot.bizconnect.enums.AlarmType;
import com.springboot.bizconnect.enums.CompanyType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyAlarmServiceImpl implements CompanyAlarmService {
    private final CompanyRepository companyRepository;
    private final CompanyRequestRepository companyRequestRepository;
    private final AlarmRepository alarmRepository;
    private final AffiliationRepository affiliationRepository;
    private final BranchRepository branchRepository;
    private final AlarmService alarmService;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public CompanyAlarmApproveResponseDto approveAlarm(CustomUserDetails userDetails, Long alarmNo) {
        Alarm alarm = alarmRepository.findById(alarmNo)
                .orElseThrow(() ->  new RuntimeException("존재하지 않는 알람입니다."));
        // 해당 알람 번호의 알람 타입이 회사인지 확인
        if (alarm.getAlarmType() != AlarmType.COMPANY_REGISTER_REQUEST) throw new RuntimeException("회사 등록 요청 알람이 아닙니다.");
        // 해당 reference_no를 가지고 branch하고 affiliation 먼저 값을 저장하고 company 등록
        CompanyRequest companyRequest = companyRequestRepository.findById(alarm.getReferenceNo())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회사 등록 요청 알림입니다"));
        if(companyRequest.getStatus() != CompanyType.PENDING) throw new RuntimeException("이미 처리된 알람입니다.");

        Affiliation affiliation = affiliationRepository.findByName(companyRequest.getAffiliationName())
                .orElseGet(() -> affiliationRepository.save(
                        Affiliation.builder()
                                .name(companyRequest.getAffiliationName())
                                .build()
                ));

        Branch branch = branchRepository.findByName(companyRequest.getBranchName())
                .orElseGet(() -> branchRepository.save(
                        Branch.builder()
                                .name(companyRequest.getBranchName())
                                .build()
                ));

        Company company = Company.builder()
                .affiliation(affiliation)
                .branch(branch)
                .name(companyRequest.getName())
                .phoneNumber(companyRequest.getPhoneNumber())
                .address(companyRequest.getAddress())
                .isDeleted(false)
                .build();

        companyRepository.save(company);

        companyRequest.setStatus(CompanyType.APPROVED);

        alarmService.sendToUser(
                userDetails.getUser(),
                companyRequest.getUser().getUserNo(),
                "회사 등록 승인",
                companyRequest.getName() + " 회사가 승인되었습니다.",
                AlarmType.GENERAL,
                null
        );
        // 승인 이후 해당 유저의 회사 변경해야함
        User requester = companyRequest.getUser();
        requester.setCompany(company);
        userRepository.save(requester);

        // return 값으로 해당 회사 등록된거 확인 message랑 저장된 정보 전부 출력하자 서비스에서는 회사 등록 + 알람 전송 + return값 설정(이걸 하는 유저의 role 확인하기)
        return CompanyAlarmApproveResponseDto.builder()
                .CompanyName(company.getName())
                .affiliationName(affiliation.getName())
                .branchName(branch.getName())
                .phoneNumber(companyRequest.getPhoneNumber())
                .address(companyRequest.getAddress())
                .createdAt(companyRequest.getCreatedAt())
                .updatedAt(companyRequest.getUpdatedAt())
                .message("회사 등록을 승인하셨습니다.")
                .build();
    }

    @Override
    @Transactional
    public CompanyAlarmRejectResponseDto rejectAlarm(CustomUserDetails userDetails, Long alarmNo, String message) {
        Alarm alarm = alarmRepository.findById(alarmNo)
                .orElseThrow(() ->  new RuntimeException("존재하지 않는 알람입니다."));

        if (alarm.getAlarmType() != AlarmType.COMPANY_REGISTER_REQUEST) throw new RuntimeException("회사 등록 요청 알람이 아닙니다.");

        CompanyRequest companyRequest = companyRequestRepository.findById(alarm.getReferenceNo())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회사 등록 요청 알림입니다"));

        if(companyRequest.getStatus() != CompanyType.PENDING) throw new RuntimeException("이미 처리된 알람입니다.");

        alarmService.sendToUser(
                userDetails.getUser(),
                companyRequest.getUser().getUserNo(),
                "회사 등록 거절",
                companyRequest.getName() + " 회사 승인이 거절되었습니다." + "\n"
                        + "사유 : " + message,
                AlarmType.GENERAL,
                null
        );

        companyRequest.setStatus(CompanyType.REJECTED);

        companyRequestRepository.save(companyRequest);

        return CompanyAlarmRejectResponseDto.builder()
                .message("회사 등록을 거절하셨습니다.")
                .build();
    }
}
