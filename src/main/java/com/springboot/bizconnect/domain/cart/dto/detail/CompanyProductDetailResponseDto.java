package com.springboot.bizconnect.domain.cart.dto.detail;

import java.time.LocalDateTime;

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
public class CompanyProductDetailResponseDto {
	private Long productNo;
	private String unitName;
	private String categoryName;
	private String manufacturerName;
	private String productStatusName;
	private String name;
	private String content;
	private Double price;
	private String imageUrl;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}

