package com.springboot.bizconnect.domain.company.service.impl;

import com.springboot.bizconnect.domain.alarm.service.Impl.AlarmServiceImpl;
import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.company.dto.create.CreateCompanyRequestDto;
import com.springboot.bizconnect.domain.company.dto.create.CreateCompanyResponseDto;
import com.springboot.bizconnect.domain.company.repository.CompanyRepository;
import com.springboot.bizconnect.domain.company.repository.CompanyRequestRepository;
import com.springboot.bizconnect.domain.company.service.CompanyService;
import com.springboot.bizconnect.domain.user.repository.UserRepository;
import com.springboot.bizconnect.entity.CompanyRequest;
import com.springboot.bizconnect.entity.User;
import com.springboot.bizconnect.enums.requestStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyRequestRepository companyRequestRepository;
    private final AlarmServiceImpl alarmService;
    private final UserRepository userRepository;

    @Override
    public CreateCompanyResponseDto createCompany(CustomUserDetails userDetails, CreateCompanyRequestDto createCompanyRequestDto) {

        String affiliationName = createCompanyRequestDto.getAffiliationName();
        if (affiliationName == null || affiliationName.trim().isEmpty()) {
            affiliationName = "없음";
        }

        String branchName = createCompanyRequestDto.getBranchName();
        if (branchName == null || branchName.trim().isEmpty()) {
            branchName = "없음";
        }

        CompanyRequest companyRequest = CompanyRequest.builder()
                .user(userDetails.getUser())
                .name(createCompanyRequestDto.getCompanyName())
                .affiliationName(affiliationName)
                .branchName(branchName)
                .address(createCompanyRequestDto.getAddress())
                .phoneNumber(createCompanyRequestDto.getPhoneNumber())
                .status(requestStatus.PENDING)
                .build();

        companyRequestRepository.save(companyRequest);

        alarmService.sendToOperationAdmins(
                userDetails.getUser(),
                "회사 등록 요청",
                userDetails.getUser().getName() + "님이 회사 등록을 요청했습니다."
        );

        return CreateCompanyResponseDto.builder()
                .message("회사 등록 요청이 완료되었습니다.")
                .build();
    }
}
