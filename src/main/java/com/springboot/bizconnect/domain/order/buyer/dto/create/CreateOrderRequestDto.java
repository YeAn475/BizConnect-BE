package com.springboot.bizconnect.domain.order.buyer.dto.create;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class CreateOrderRequestDto {
    private Long supplierCompanyNo;
    private List<OrderItemDto> orderItems;

    @Getter
    @Setter
    public static class OrderItemDto {
        private Long productNo;
        private Integer quantity;
    }
}
