package com.springboot.bizconnect.domain.order.controller;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.order.dto.create.CreateOrderRequestDto;
import com.springboot.bizconnect.domain.order.dto.create.CreateOrderResponseDto;
import com.springboot.bizconnect.domain.order.service.BuyerOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
@Tag(name = "BuyerOrder", description = "주문 관련 API")
public class BuyerOrderController {

    private final BuyerOrderService buyerOrderService;
    /*
    - 주문 생성 (공급사에 상품 주문)
      POST /api/order
      param: supplierCompanyNo, List<productNo, quantity>

    - 주문 상세 조회 (주문 상태 확인)
      GET /api/order/{orderNo}
      param: orderNo

    - 주문 취소
      PATCH /api/order/{orderNo}/cancel
      param: orderNo

    - 주문 이력 (내 주문 목록)
      GET /api/order/list
      param: page, size
     */
    @GetMapping("/")
    @Operation(summary = "발주", description = "회사에서 공급사에 발주를 넣는다.")
    public ResponseEntity<CreateOrderResponseDto> createOrder(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ParameterObject CreateOrderRequestDto requestDto
            ) {
        CreateOrderResponseDto responseDto = buyerOrderService.createOrder(userDetails, requestDto);
        return ResponseEntity.ok(responseDto);
    }
}
