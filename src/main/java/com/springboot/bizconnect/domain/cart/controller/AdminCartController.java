package com.springboot.bizconnect.domain.cart.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.cart.dto.assign.AssignProductRequestDto;
import com.springboot.bizconnect.domain.cart.dto.assign.AssignProductResponseDto;
import com.springboot.bizconnect.domain.cart.service.AdminCartService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/cart")
@Tag(name = "AdminCart", description = "운영 관리자 장바구니 관련 API")
public class AdminCartController {
	private final AdminCartService admincartservice;
	
	// 기존 데이터가 없으면 생성 있으면 추가입니다.
	@PostMapping("/assign")
	@Operation(summary = "거래처 상품 배정", description = "관리자가 특정 업체에 상품들을 배정합니다.")
	public ResponseEntity<AssignProductResponseDto> assignProduct(
			@AuthenticationPrincipal CustomUserDetails userDetails,
			@ParameterObject AssignProductRequestDto requestDto
    ) {
		AssignProductResponseDto responseDto = admincartservice.assignProduct(userDetails, requestDto);
		return ResponseEntity.ok(responseDto);
	}
	
	@PatchMapping("/{productNo}/update")
	@Operation(summary = "거래처 상품 사용 여부 변경", description = "운영 관리자가 거래처 장바구니에 상품의 사용 여부를 변경합니다.")
	public ResponseEntity<?> UpdateCompanyProductCart() {
		return null;
	}
	
	@GetMapping("/companies/{companyNo}")
    @Operation(summary = "특정 업체 배정 상품 조회", description = "관리자가 특정 업체의 장바구니를 조회합니다.")
    public ResponseEntity<?> getCompanyCart() {
        // is_used가 true인 값
        return null;
    }
	
	@GetMapping("/companies/")
    @Operation(summary = "업체 배정 상품 리스트조회", description = "관리자가 특정 업체의 장바구니 리스트를 조회합니다.")
    public ResponseEntity<?> getCompanyCartList() {
        return null;
    }
	
}
