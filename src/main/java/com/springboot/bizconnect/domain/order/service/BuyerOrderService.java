package com.springboot.bizconnect.domain.order.service;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.order.dto.create.CreateOrderRequestDto;
import com.springboot.bizconnect.domain.order.dto.create.CreateOrderResponseDto;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

public interface BuyerOrderService {
    CreateOrderResponseDto createOrder(@AuthenticationPrincipal CustomUserDetails userDetails, @ParameterObject CreateOrderRequestDto requestDto);
}
