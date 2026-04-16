package com.springboot.bizconnect.domain.cart.dto.update;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateCartProductRequestDto {
    private Long buyerCompanyNo;
    private List<Long> productNos;
}
