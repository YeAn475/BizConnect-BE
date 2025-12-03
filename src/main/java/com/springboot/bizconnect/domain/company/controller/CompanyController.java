package com.springboot.bizconnect.domain.company.controller;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.company.dto.create.CreateCompanyRequestDto;
import com.springboot.bizconnect.domain.company.dto.create.CreateCompanyResponseDto;
import com.springboot.bizconnect.domain.company.service.CompanyService;
import com.springboot.bizconnect.entity.Company;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/company")
@Tag(name = "Company", description = "회사 관련 API")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    // 회사 등록 요청
    @PostMapping("/request")
    @Operation(summary = "회사 등록 요청", description = "회사 등록을 운영관리자에게 요청합니다.")
    ResponseEntity<CreateCompanyResponseDto> createCompany(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ParameterObject CreateCompanyRequestDto requestDto) {
        CreateCompanyResponseDto responseDto = companyService.createCompany(userDetails, requestDto);
        return ResponseEntity.ok(responseDto);
    }
    // 회사 요청 승인
    // 회사 정보 수정
    // 법인계좌 추가
    // 사업자 번호 추가
    // 회사 삭제 요청
}
