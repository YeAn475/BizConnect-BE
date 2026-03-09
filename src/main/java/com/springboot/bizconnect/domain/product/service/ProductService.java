package com.springboot.bizconnect.domain.product.service;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.product.dto.create.CreateProductRequestDto;
import com.springboot.bizconnect.domain.product.dto.create.CreateProductResponseDto;
import com.springboot.bizconnect.domain.product.dto.list.ProductListRequestDto;
import com.springboot.bizconnect.domain.product.dto.list.ProductListResponseDto;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

public interface ProductService {
    CreateProductResponseDto createProduct(@AuthenticationPrincipal CustomUserDetails userDetails, @ParameterObject CreateProductRequestDto requestDto);
    
    List<ProductListResponseDto> getProductList(@ParameterObject ProductListRequestDto requestDto);
}
