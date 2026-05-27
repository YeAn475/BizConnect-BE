package com.springboot.bizconnect.domain.order.buyer.dto.status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyerOrderStatusRequestDto {
    private Long orderNo;
}
