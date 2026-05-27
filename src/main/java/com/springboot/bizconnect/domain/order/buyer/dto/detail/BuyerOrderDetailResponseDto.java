package com.springboot.bizconnect.domain.order.buyer.dto.detail;

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
public class BuyerOrderDetailResponseDto {
    private Long orderNo;
    private String supplierCompanyName;
    private String status;
    private LocalDateTime createdAt;
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
