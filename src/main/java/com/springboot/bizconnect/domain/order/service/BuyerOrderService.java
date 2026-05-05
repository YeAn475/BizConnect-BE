package com.springboot.bizconnect.domain.order.service;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.order.dto.cancel.OrderCancelRequestDto;
import com.springboot.bizconnect.domain.order.dto.cancel.OrderCancelResponseDto;
import com.springboot.bizconnect.domain.order.dto.create.CreateOrderRequestDto;
import com.springboot.bizconnect.domain.order.dto.create.CreateOrderResponseDto;
import com.springboot.bizconnect.domain.order.dto.detail.BuyerOrderDetailRequestDto;
import com.springboot.bizconnect.domain.order.dto.detail.BuyerOrderDetailResponseDto;
import com.springboot.bizconnect.domain.order.dto.list.BuyerOrderListRequestDto;
import com.springboot.bizconnect.domain.order.dto.list.BuyerOrderListResponseDto;
import com.springboot.bizconnect.domain.order.dto.status.BuyerOrderStatusRequestDto;
import com.springboot.bizconnect.domain.order.dto.status.BuyerOrderStatusResponseDto;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

public interface BuyerOrderService {
    CreateOrderResponseDto createOrder(@AuthenticationPrincipal CustomUserDetails userDetails, @ParameterObject CreateOrderRequestDto requestDto);
    List<BuyerOrderListResponseDto> OrderList(@AuthenticationPrincipal CustomUserDetails userDetails, @ParameterObject BuyerOrderListRequestDto requestDto);
    BuyerOrderDetailResponseDto orderDetail(@AuthenticationPrincipal CustomUserDetails userDetails, @ParameterObject BuyerOrderDetailRequestDto requestDto);
    BuyerOrderStatusResponseDto orderStatusDetail(@AuthenticationPrincipal CustomUserDetails userDetails, @ParameterObject BuyerOrderStatusRequestDto requestDto);
    OrderCancelResponseDto orderCancel(@AuthenticationPrincipal CustomUserDetails userDetails, @ParameterObject OrderCancelRequestDto requestDto);
}
