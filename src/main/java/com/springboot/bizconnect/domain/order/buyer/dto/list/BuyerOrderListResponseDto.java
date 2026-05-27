package com.springboot.bizconnect.domain.order.buyer.dto.list;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyerOrderListResponseDto {
    private Long orderNo;
    private String supplierCompanyName;
    private String orderStatus;
    private LocalDateTime createdAt;
}
