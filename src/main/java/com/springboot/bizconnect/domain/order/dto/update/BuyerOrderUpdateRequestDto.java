package com.springboot.bizconnect.domain.order.dto.update;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BuyerOrderUpdateRequestDto {
    private Long orderNo;
    private List<OrderItemDto> orderItems;  // 내부 클래스 사용

    @Getter
    @Setter
    public static class OrderItemDto {
        private Long productNo;
        private Integer quantity;
    }
}