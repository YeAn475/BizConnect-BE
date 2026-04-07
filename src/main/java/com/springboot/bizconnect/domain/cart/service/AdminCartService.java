package com.springboot.bizconnect.domain.cart.service;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.cart.dto.assign.AssignProductRequestDto;
import com.springboot.bizconnect.domain.cart.dto.assign.AssignProductResponseDto;
import com.springboot.bizconnect.domain.cart.dto.list.ProductAdminCartListRequestDto;
import com.springboot.bizconnect.domain.cart.dto.list.ProductAdminCartListResponseDto;

public interface AdminCartService {
	AssignProductResponseDto assignProduct(@AuthenticationPrincipal CustomUserDetails userDetails,@ParameterObject AssignProductRequestDto requestDto);
	List<ProductAdminCartListResponseDto> getCompanyCartList(@AuthenticationPrincipal CustomUserDetails userDetails,@ParameterObject ProductAdminCartListRequestDto requestDto);
}
