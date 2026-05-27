package com.springboot.bizconnect.domain.order.supplier.dto.list;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierOrderListResponseDto {
    private Long orderNo;
    private String buyerCompanyName;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
