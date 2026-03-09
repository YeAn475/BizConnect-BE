package com.springboot.bizconnect.domain.product.dto.list;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductListResponseDto {
	private int productNo;
	private String name;
	private String imageUrl;

}
