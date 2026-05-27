package com.springboot.bizconnect.domain.order.supplier.controller;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.order.supplier.dto.detail.SupplierOrderDetailRequestDto;
import com.springboot.bizconnect.domain.order.supplier.dto.detail.SupplierOrderDetailResponseDto;
import com.springboot.bizconnect.domain.order.supplier.dto.list.SupplierOrderListRequestDto;
import com.springboot.bizconnect.domain.order.supplier.dto.list.SupplierOrderListResponseDto;
import com.springboot.bizconnect.domain.order.supplier.dto.status.SupplierOrderStatusUpdateRequestDto;
import com.springboot.bizconnect.domain.order.supplier.dto.status.SupplierOrderStatusUpdateResponseDto;
import com.springboot.bizconnect.domain.order.supplier.service.SupplierOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/SupplierOrder")
@Tag(name = "SupplierOrder", description = "발급사 주문 관련 API")
public class SupplierOrderController {
    private final SupplierOrderService supplierOrderService;
    /*
    발주받은이력조회

    발주상태 변경

    이력상세보기
     */
    @GetMapping("/list")
    @Operation(summary = "발주 받은 이력 조회", description = "우리 회사가 받은 발주 목록을 조회합니다.")
    public ResponseEntity<List<SupplierOrderListResponseDto>> getOrderList(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ParameterObject SupplierOrderListRequestDto requestDto
    ) {
        List<SupplierOrderListResponseDto> responseDto = supplierOrderService.getOrderList(userDetails, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/status")
    @Operation(summary = "발주 상태 변경", description = "발주 상태를 승인/거절합니다.")
    public ResponseEntity<SupplierOrderStatusUpdateResponseDto> updateOrderStatus(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ParameterObject SupplierOrderStatusUpdateRequestDto requestDto
    ) {
        SupplierOrderStatusUpdateResponseDto responseDto = supplierOrderService.updateOrderStatus(userDetails, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/detail")
    @Operation(summary = "이력 상세 조회", description = "발주 이력을 상세 조회합니다.")
    public ResponseEntity<SupplierOrderDetailResponseDto> getOrderDetail(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ParameterObject SupplierOrderDetailRequestDto requestDto
    ) {
        SupplierOrderDetailResponseDto responseDto = supplierOrderService.getOrderDetail(userDetails, requestDto);
        return ResponseEntity.ok(responseDto);
    }

}
