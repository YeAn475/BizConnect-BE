package com.springboot.bizconnect.domain.order.supplier.dto.status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierOrderStatusUpdateRequestDto {
    private Long orderNo;
    private String status;
}
