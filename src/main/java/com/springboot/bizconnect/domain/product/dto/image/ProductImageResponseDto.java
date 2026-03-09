package com.springboot.bizconnect.domain.product.dto.image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ProductImageResponseDto {
	private String message;
	private String url;
}
