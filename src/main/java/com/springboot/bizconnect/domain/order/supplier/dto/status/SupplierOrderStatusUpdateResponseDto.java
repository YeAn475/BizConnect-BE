package com.springboot.bizconnect.domain.order.supplier.dto.status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierOrderStatusUpdateResponseDto {
    private Long orderNo;
    private String status;
    private String message;
}