package com.springboot.bizconnect.domain.company.controller;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.company.dto.account.AccountReqeustDto;
import com.springboot.bizconnect.domain.company.dto.account.AccountResponseDto;
import com.springboot.bizconnect.domain.company.dto.create.CreateCompanyRequestDto;
import com.springboot.bizconnect.domain.company.dto.create.CreateCompanyResponseDto;
import com.springboot.bizconnect.domain.company.dto.list.CompanyListRequestDto;
import com.springboot.bizconnect.domain.company.dto.list.CompanyListResponseDto;
import com.springboot.bizconnect.domain.company.dto.update.UpdateCompanyRequestDto;
import com.springboot.bizconnect.domain.company.dto.update.UpdateCompanyResponseDto;
import com.springboot.bizconnect.domain.company.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    // 회사 정보 수정
    @PatchMapping("/")
    @Operation(summary = "회저 정보 수정", description = "회사 정보를 수정합니다.")
    ResponseEntity<UpdateCompanyResponseDto> updateCompany(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ParameterObject UpdateCompanyRequestDto requestDto
    ) {
        UpdateCompanyResponseDto responseDto = companyService.updateCompany(userDetails, requestDto);
        return ResponseEntity.ok(responseDto);
    }
    // 회사 리스트 확인
    @GetMapping("/list")
    @Operation(summary = "모든 회사 리스트 조회", description = "모든 회사 리스트를 조회합니다")
    ResponseEntity<List<CompanyListResponseDto>> listCompany(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ParameterObject CompanyListRequestDto requestDto
    ) {
        List<CompanyListResponseDto> responseDto = companyService.companyList(userDetails, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    // 법인계좌 추가
    @PostMapping("/account")
    @Operation(summary = "법인계좌 추가", description = "회사관리자가 법인계좌를 추가합니다.")
    ResponseEntity<AccountResponseDto> createCompanyAccount(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ParameterObject AccountReqeustDto requestDto
    ) {
        AccountResponseDto responseDto = companyService.createCompanyAccount(userDetails, requestDto);
        return ResponseEntity.ok(responseDto);
    }



    // 사업자 번호 추가
    // 회사 삭제 요청
}
