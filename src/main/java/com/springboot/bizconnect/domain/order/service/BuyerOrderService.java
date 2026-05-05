package com.springboot.bizconnect.domain.order.service;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.order.dto.cancel.BuyerOrderCancelRequestDto;
import com.springboot.bizconnect.domain.order.dto.cancel.BuyerOrderCancelResponseDto;
import com.springboot.bizconnect.domain.order.dto.create.CreateOrderRequestDto;
import com.springboot.bizconnect.domain.order.dto.create.CreateOrderResponseDto;
import com.springboot.bizconnect.domain.order.dto.detail.BuyerOrderDetailRequestDto;
import com.springboot.bizconnect.domain.order.dto.detail.BuyerOrderDetailResponseDto;
import com.springboot.bizconnect.domain.order.dto.list.BuyerOrderListRequestDto;
import com.springboot.bizconnect.domain.order.dto.list.BuyerOrderListResponseDto;
import com.springboot.bizconnect.domain.order.dto.status.BuyerOrderStatusRequestDto;
import com.springboot.bizconnect.domain.order.dto.status.BuyerOrderStatusResponseDto;
import com.springboot.bizconnect.domain.order.dto.update.BuyerOrderUpdateRequestDto;
import com.springboot.bizconnect.domain.order.dto.update.BuyerOrderUpdateResponseDto;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

public interface BuyerOrderService {
    CreateOrderResponseDto createOrder(@AuthenticationPrincipal CustomUserDetails userDetails, @ParameterObject CreateOrderRequestDto requestDto);
    List<BuyerOrderListResponseDto> OrderList(@AuthenticationPrincipal CustomUserDetails userDetails, @ParameterObject BuyerOrderListRequestDto requestDto);
    BuyerOrderDetailResponseDto orderDetail(@AuthenticationPrincipal CustomUserDetails userDetails, @ParameterObject BuyerOrderDetailRequestDto requestDto);
    BuyerOrderStatusResponseDto orderStatusDetail(@AuthenticationPrincipal CustomUserDetails userDetails, @ParameterObject BuyerOrderStatusRequestDto requestDto);
    BuyerOrderCancelResponseDto orderCancel(@AuthenticationPrincipal CustomUserDetails userDetails, @ParameterObject BuyerOrderCancelRequestDto requestDto);
    BuyerOrderUpdateResponseDto orderUpdate(@AuthenticationPrincipal CustomUserDetails userDetails, @ParameterObject BuyerOrderUpdateRequestDto requestDto);
}
