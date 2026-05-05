package com.springboot.bizconnect.domain.order.controller;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.order.dto.cancel.BuyerOrderCancelRequestDto;
import com.springboot.bizconnect.domain.order.dto.cancel.BuyerOrderCancelResponseDto;
import com.springboot.bizconnect.domain.order.dto.create.CreateOrderRequestDto;
import com.springboot.bizconnect.domain.order.dto.create.CreateOrderResponseDto;
import com.springboot.bizconnect.domain.order.dto.detail.BuyerOrderDetailRequestDto;
import com.springboot.bizconnect.domain.order.dto.detail.BuyerOrderDetailResponseDto;
import com.springboot.bizconnect.domain.order.dto.status.BuyerOrderStatusRequestDto;
import com.springboot.bizconnect.domain.order.dto.status.BuyerOrderStatusResponseDto;
import com.springboot.bizconnect.domain.order.dto.list.BuyerOrderListRequestDto;
import com.springboot.bizconnect.domain.order.dto.list.BuyerOrderListResponseDto;
import com.springboot.bizconnect.domain.order.dto.update.BuyerOrderUpdateRequestDto;
import com.springboot.bizconnect.domain.order.dto.update.BuyerOrderUpdateResponseDto;
import com.springboot.bizconnect.domain.order.service.BuyerOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    - 주문 생성(발주) (공급사에 상품 주문)
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

      - 주문 수정


      주문 상태 보기 vs 주문 상세 보기
      현재 주문 상태 즉 pending인지 확인 vs 본인 회사가 어떤 목록의 주문을 했는지 확인
     */
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

    @GetMapping("/{orderNo}")
    @Operation(summary = "주문 상세 보기", description = "내가 넣은 주문내역을 상세히 확인합니다.")
    public ResponseEntity<BuyerOrderDetailResponseDto> orderDetail(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ParameterObject BuyerOrderDetailRequestDto requestDto
            ) {
        BuyerOrderDetailResponseDto responseDto = buyerOrderService.orderDetail(userDetails, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{orderNo}")
    @Operation(summary = "주문 상태 보기", description = "내가 넣은 주문상태 상세히 확인합니다.")
    public ResponseEntity<BuyerOrderStatusResponseDto> orderStatusDetail(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ParameterObject BuyerOrderStatusRequestDto requestDto
    ) {
        BuyerOrderStatusResponseDto responseDto = buyerOrderService.orderStatusDetail(userDetails, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{orderNo}/cancel")
    @Operation(summary = "주문 취소", description = "내가 넣은 주문을 취소합니다.")
    public ResponseEntity<BuyerOrderCancelResponseDto> orderCancel(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ParameterObject BuyerOrderCancelRequestDto requestDto
    ) {
        BuyerOrderCancelResponseDto responseDto = buyerOrderService.orderCancel(userDetails, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{orderNo}/update")
    @Operation(summary = "주문 수정", description = "내가 넣은 주문을 수정합니다.")
    public ResponseEntity<BuyerOrderUpdateResponseDto> orderUpdate(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ParameterObject BuyerOrderUpdateRequestDto requestDto
    ) {
        BuyerOrderUpdateResponseDto responseDto = buyerOrderService.orderUpdate(userDetails, requestDto);
        return ResponseEntity.ok(responseDto);
    }
}
