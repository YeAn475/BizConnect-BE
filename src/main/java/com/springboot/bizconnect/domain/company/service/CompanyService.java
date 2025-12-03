package com.springboot.bizconnect.domain.company.service;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.company.dto.create.CreateCompanyRequestDto;
import com.springboot.bizconnect.domain.company.dto.create.CreateCompanyResponseDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface CompanyService {
    CreateCompanyResponseDto createCompany(
            CustomUserDetails userDetails,
            CreateCompanyRequestDto createCompanyRequestDto);


}
