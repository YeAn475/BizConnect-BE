package com.springboot.bizconnect.domain.company.service;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.company.dto.create.CreateCompanyRequestDto;
import com.springboot.bizconnect.domain.company.dto.create.CreateCompanyResponseDto;
import com.springboot.bizconnect.domain.company.dto.list.CompanyListRequestDto;
import com.springboot.bizconnect.domain.company.dto.list.CompanyListResponseDto;
import com.springboot.bizconnect.domain.company.dto.update.UpdateCompanyRequestDto;
import com.springboot.bizconnect.domain.company.dto.update.UpdateCompanyResponseDto;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface CompanyService {
    CreateCompanyResponseDto createCompany(
            CustomUserDetails userDetails,
            CreateCompanyRequestDto createCompanyRequestDto);

    UpdateCompanyResponseDto updateCompany(
            CustomUserDetails userDetails,
            UpdateCompanyRequestDto requestDto
    );
    List<CompanyListResponseDto> companyList(CustomUserDetails userDetails, CompanyListRequestDto requestDto);

}
