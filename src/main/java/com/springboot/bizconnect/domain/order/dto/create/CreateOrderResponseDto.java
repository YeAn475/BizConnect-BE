package com.springboot.bizconnect.domain.order.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CreateOrderResponseDto {
    private Long orderNo;
    private String message;
}
