package com.springboot.bizconnect.domain.order.dto.status;

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
public class BuyerOrderStatusResponseDto {
    private String status;
}
