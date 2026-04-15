package com.springboot.bizconnect.domain.order.controller;

import com.springboot.bizconnect.domain.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
@Tag(name = "Order", description = "주문 관련 API")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/order")
    @Operation(summary = "상품을 주문합니다.", description = "유저가 선택한 상품들은 주문합니다.")
    public ResponseEntity<?> ProductsOrder() {
        return null;
    }
}
