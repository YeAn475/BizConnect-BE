package com.springboot.bizconnect.domain.cart.dto.list;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class ProductAdminCartListResponseDto {
    private Long buyerCompanyNo;
    private String buyerCompanyName;
}
