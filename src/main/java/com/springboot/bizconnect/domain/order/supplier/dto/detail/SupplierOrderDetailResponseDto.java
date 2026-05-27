package com.springboot.bizconnect.domain.order.supplier.dto.detail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierOrderDetailResponseDto {
    private Long orderNo;
    private String buyerCompanyName;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<OrderItemDetailDto> orderItems;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemDetailDto {
        private Long productNo;
        private String productName;
        private Integer quantity;
    }
}