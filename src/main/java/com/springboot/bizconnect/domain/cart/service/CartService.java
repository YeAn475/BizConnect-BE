package com.springboot.bizconnect.domain.cart.service;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.cart.dto.detail.CompanyProductDetailRequestDto;
import com.springboot.bizconnect.domain.cart.dto.detail.CompanyProductDetailResponseDto;
import com.springboot.bizconnect.domain.product.dto.list.ProductListRequestDto;
import com.springboot.bizconnect.domain.product.dto.list.ProductListResponseDto;

public interface CartService {
	List<ProductListResponseDto> getCartProductList(@AuthenticationPrincipal CustomUserDetails userDetails, @ParameterObject ProductListRequestDto requestDto);
	
	CompanyProductDetailResponseDto getCartProductDetail(@ParameterObject CompanyProductDetailRequestDto requestDto, @AuthenticationPrincipal CustomUserDetails userDetails);

}
