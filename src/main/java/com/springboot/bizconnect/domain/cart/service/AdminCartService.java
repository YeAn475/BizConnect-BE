package com.springboot.bizconnect.domain.cart.service;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.cart.dto.assign.AssignProductRequestDto;
import com.springboot.bizconnect.domain.cart.dto.assign.AssignProductResponseDto;

public interface AdminCartService {
	AssignProductResponseDto assignProduct(@AuthenticationPrincipal CustomUserDetails userDetails,@ParameterObject AssignProductRequestDto requestDto);
}
