package com.springboot.bizconnect.domain.cart.dto.assign;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignProductRequestDto {
	private Long buyerCompanyNo;
	private List<Long> productNo;
}
