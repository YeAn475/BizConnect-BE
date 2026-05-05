package com.springboot.bizconnect.domain.cart.controller;

import java.util.List;

import com.springboot.bizconnect.domain.cart.dto.list.CompanyProductListRequestDto;
import com.springboot.bizconnect.domain.cart.dto.list.CompanyProductListResponseDto;
import com.springboot.bizconnect.domain.cart.dto.update.UpdateCartProductRequestDto;
import com.springboot.bizconnect.domain.cart.dto.update.UpdateCartProductResponseDto;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.cart.dto.assign.AssignProductRequestDto;
import com.springboot.bizconnect.domain.cart.dto.assign.AssignProductResponseDto;
import com.springboot.bizconnect.domain.cart.dto.list.ProductAdminCartListRequestDto;
import com.springboot.bizconnect.domain.cart.dto.list.ProductAdminCartListResponseDto;
import com.springboot.bizconnect.domain.cart.service.AdminCartService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/cart")
@Tag(name = "SupplierCart", description = "공급사 장바구니 관련 API")
public class SupplierCartController {
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
	
	
	@GetMapping("/companies/")
	@Operation(summary = "업체 배정 상품 리스트 조회", description = "관리자가 특정 업체의 장바구니 리스트를 조회합니다.")
	public ResponseEntity<List<ProductAdminCartListResponseDto>> getCompanyCartList(
			@AuthenticationPrincipal CustomUserDetails userDetails,
			@ParameterObject ProductAdminCartListRequestDto requestDto
		) {
		// 거래중인 업체 회사들을 조회
		List<ProductAdminCartListResponseDto> responseDto = admincartservice.getCompanyCartList(userDetails, requestDto);
		return ResponseEntity.ok(responseDto);
	}
	
	
	@GetMapping("/companies/{buyerCompanyNo}")
    @Operation(summary = "특정 업체 배정 상품 조회", description = "관리자가 특정 업체의 장바구니를 조회합니다.")
    public ResponseEntity<List<CompanyProductListResponseDto>> getCompanyCart(
    		@AuthenticationPrincipal CustomUserDetails userDetails,
		    @PathVariable Long buyerCompanyNo,
			@ParameterObject CompanyProductListRequestDto requestDto
	) {
		// 해당 업체에 존재하는 상품일것
        // 해당 상품의 is_used가 true인 값들 모두 조회
		List<CompanyProductListResponseDto> responseDto = admincartservice.getCompanyCart(userDetails, buyerCompanyNo, requestDto);
        return ResponseEntity.ok(responseDto);
    }
	
	@PatchMapping("/update")
	@Operation(summary = "거래처 상품 사용 여부 변경", description = "운영 관리자가 거래처 장바구니에 상품의 사용 여부를 변경합니다.")
	public ResponseEntity<UpdateCartProductResponseDto> UpdateCompanyProductCart(
			@AuthenticationPrincipal CustomUserDetails userDetails,
			@ParameterObject UpdateCartProductRequestDto requestDto
	) {
		// request 값으로 productNo값을 넘기면 해당 회사 장바구니에 상품이 존재하는 확인 절차 후 is_used의 반대로 변경
		UpdateCartProductResponseDto responseDto = admincartservice.UpdateCompanyProductCart(userDetails, requestDto);
		return ResponseEntity.ok(responseDto);
	}
}
