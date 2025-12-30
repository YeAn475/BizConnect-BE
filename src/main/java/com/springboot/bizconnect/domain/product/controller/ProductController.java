package com.springboot.bizconnect.domain.product.controller;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
    /*
    제조사 등록
    상품 등록
    상품 리스트 조회
    상품 등록 삭제
    상품 상세보기
     */

    /*
    본사에서 직접 만들경우 생각(mega커피 본사에서 직접 원두를 만들경우)
     */
    @PostMapping("/manufacturer")
    @Operation(summary = "제조사 등록", description = "운영 관리자가 제조사를 등록합니다.")
    public ResponseEntity<?> createManufacturer(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return null;
    }
    @PostMapping("/")
    @Operation (summary = "상품 등록", description = "운영 관리자가 상품을 등록합니다.")
    public ResponseEntity<?> createProduct() {
        return null;
    }
    @PutMapping("/")
    @Operation(summary = "상품 등록 해제", description = "운영 관리자가 상품을 삭제합니다.")
    public ResponseEntity<?> deleteProduct() {
        return null;
    }

}
