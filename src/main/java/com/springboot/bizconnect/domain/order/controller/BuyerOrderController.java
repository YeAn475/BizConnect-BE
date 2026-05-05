package com.springboot.bizconnect.domain.order.controller;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.order.dto.create.CreateOrderRequestDto;
import com.springboot.bizconnect.domain.order.dto.create.CreateOrderResponseDto;
import com.springboot.bizconnect.domain.order.dto.list.BuyerOrderListRequestDto;
import com.springboot.bizconnect.domain.order.dto.list.BuyerOrderListResponseDto;
import com.springboot.bizconnect.domain.order.service.BuyerOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

      - 주문 이력 상세 보기
     */

    // 내가 생각하는 발주 입력이 아님(한번 어떤식으로 해야 할지 물어보기)
    @PostMapping("/")
    @Operation(summary = "발주", description = "회사에서 공급사에 발주를 넣습니다.")
    public ResponseEntity<CreateOrderResponseDto> createOrder(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody CreateOrderRequestDto requestDto
            ) {
        CreateOrderResponseDto responseDto = buyerOrderService.createOrder(userDetails, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/list")
    @Operation(summary = "주문 이력", description = "유저 회사 주문 이력을 확인합니다.")
    public ResponseEntity<List<BuyerOrderListResponseDto>> OrderList(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ParameterObject BuyerOrderListRequestDto requestDto
    ) {
        List<BuyerOrderListResponseDto> responseDto = buyerOrderService.OrderList(userDetails, requestDto);
        return ResponseEntity.ok(responseDto);
    }
}
