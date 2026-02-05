package com.springboot.bizconnect.domain.product.service;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.product.dto.create.CreateProductRequestDto;
import com.springboot.bizconnect.domain.product.dto.create.CreateProductResponseDto;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

public interface ProductService {
    CreateProductResponseDto createProduct(@AuthenticationPrincipal CustomUserDetails userDetails, @ParameterObject CreateProductRequestDto requestDto);
}
