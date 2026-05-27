package com.springboot.bizconnect.domain.order.buyer.dto.update;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuyerOrderUpdateResponseDto {
    private String message;
}
