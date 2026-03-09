package com.springboot.bizconnect.domain.product.controller;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.product.dto.create.CreateProductRequestDto;
import com.springboot.bizconnect.domain.product.dto.create.CreateProductResponseDto;
import com.springboot.bizconnect.domain.product.dto.list.ProductListRequestDto;
import com.springboot.bizconnect.domain.product.dto.list.ProductListResponseDto;
import com.springboot.bizconnect.domain.product.service.ProductService;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
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

    @PostMapping("/")
    @Operation (summary = "상품 등록", description = "운영 관리자가 상품을 등록합니다.")
    public ResponseEntity<CreateProductResponseDto> createProduct(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ParameterObject CreateProductRequestDto requestDto
    ) {
        CreateProductResponseDto responseDto = productService.createProduct(userDetails, requestDto);
        return ResponseEntity.ok(responseDto);
    }
    
    @PostMapping("/list")
    @Operation(summary = "상품 리스트 조회", description = "상품 리스트를 조회합니다.")
    public ResponseEntity<List<ProductListResponseDto>> getProductList(
    		 @ParameterObject ProductListRequestDto requestDto
    		) {
    	List<ProductListResponseDto> responseDto = productService.getProductList(requestDto);
    	return ResponseEntity.ok(responseDto);
    }
    
    @PostMapping("/{productNo}")
    @Operation(summary = "상제 상품 조회", description = "상세 상품을 조회합니다.")
    public ResponseEntity<?> ProductDetail() {
    	return null;
    }
    
    @PatchMapping("/")
    @Operation(summary = "상품 정보 수정", description = "상품 정보를 수정합니다.")
    public ResponseEntity<?> UpdateProduct() {
    	return null;
    }

    @PutMapping("/")
    @Operation(summary = "상품 등록 해제", description = "운영 관리자가 상품을 삭제합니다.")
    public ResponseEntity<?> deleteProduct() {
        return null;
    }

}
