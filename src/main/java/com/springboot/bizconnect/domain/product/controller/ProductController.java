package com.springboot.bizconnect.domain.product.controller;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.product.dto.create.CreateProductRequestDto;
import com.springboot.bizconnect.domain.product.dto.create.CreateProductResponseDto;
import com.springboot.bizconnect.domain.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
@Tag(name = "Product", description = "상품 관련 API")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    /*
    상품 등록
    상품 리스트 조회
    상품 등록 삭제
    상품 상세보기
    상품 상태 변경
     */
    @PostMapping("/")
    @Operation (summary = "상품 등록", description = "운영 관리자가 상품을 등록합니다.")
    public ResponseEntity<CreateProductResponseDto> createProduct(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ParameterObject CreateProductRequestDto requestDto
    ) {
        CreateProductResponseDto responseDto = productService.createProduct(userDetails, requestDto);
        return ResponseEntity.ok(responseDto);
    }


    @PutMapping("/")
    @Operation(summary = "상품 등록 해제", description = "운영 관리자가 상품을 삭제합니다.")
    public ResponseEntity<?> deleteProduct() {
        return null;
    }

}
