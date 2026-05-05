package com.springboot.bizconnect.domain.order.service;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.order.dto.create.CreateOrderRequestDto;
import com.springboot.bizconnect.domain.order.dto.create.CreateOrderResponseDto;
import com.springboot.bizconnect.domain.order.dto.list.BuyerOrderListRequestDto;
import com.springboot.bizconnect.domain.order.dto.list.BuyerOrderListResponseDto;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

public interface BuyerOrderService {
    CreateOrderResponseDto createOrder(@AuthenticationPrincipal CustomUserDetails userDetails, @ParameterObject CreateOrderRequestDto requestDto);
    List<BuyerOrderListResponseDto> OrderList(@AuthenticationPrincipal CustomUserDetails userDetails, @ParameterObject BuyerOrderListRequestDto requestDto);
}
