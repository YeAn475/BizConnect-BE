package com.springboot.bizconnect.domain.cart.service;

import java.util.List;

import com.springboot.bizconnect.domain.cart.dto.list.CompanyProductListRequestDto;
import com.springboot.bizconnect.domain.cart.dto.list.CompanyProductListResponseDto;
import com.springboot.bizconnect.domain.cart.dto.update.UpdateCartProductRequestDto;
import com.springboot.bizconnect.domain.cart.dto.update.UpdateCartProductResponseDto;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.cart.dto.assign.AssignProductRequestDto;
import com.springboot.bizconnect.domain.cart.dto.assign.AssignProductResponseDto;
import com.springboot.bizconnect.domain.cart.dto.list.ProductAdminCartListRequestDto;
import com.springboot.bizconnect.domain.cart.dto.list.ProductAdminCartListResponseDto;

public interface AdminCartService {
	AssignProductResponseDto assignProduct(@AuthenticationPrincipal CustomUserDetails userDetails,@ParameterObject AssignProductRequestDto requestDto);
	List<ProductAdminCartListResponseDto> getCompanyCartList(@AuthenticationPrincipal CustomUserDetails userDetails,@ParameterObject ProductAdminCartListRequestDto requestDto);
	List<CompanyProductListResponseDto> getCompanyCart(CustomUserDetails userDetails, Long buyerCompanyNo, CompanyProductListRequestDto requestDto);
	UpdateCartProductResponseDto UpdateCompanyProductCart(@AuthenticationPrincipal CustomUserDetails userDetails, @ParameterObject UpdateCartProductRequestDto requestDto);
}
