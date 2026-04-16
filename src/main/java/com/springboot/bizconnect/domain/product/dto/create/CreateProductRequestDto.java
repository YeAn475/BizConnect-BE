package com.springboot.bizconnect.domain.product.dto.create;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProductRequestDto {
    @Schema(description = "상품명", example = "테스트 상품")
    private String name;

    @Schema(description = "회사명", example = "TestCompany")
    private String companyName;

    @Schema(description = "단위명", example = "EA")
    private String unit;

    @Schema(description = "카테고리명", example = "소모품")
    private String category;

    @Schema(description = "제조사명", example = "A")
    private String manufacturer;

    @Schema(description = "상품 상태", example = "판매중")
    private String productStatus;

    @Schema(description = "상품 설명")
    private String content;

    @Schema(description = "가격", example = "10000")
    private Double price;

    @Schema(description = "이미지 URL")
    private String imageUrl;
}
