package com.springboot.bizconnect.domain.cart.dto.list;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SupplierListResponseDto {
    private Long supplierCompanyNo;
    private String supplierCompanyName;
}
