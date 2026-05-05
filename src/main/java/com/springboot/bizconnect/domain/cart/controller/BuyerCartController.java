package com.springboot.bizconnect.domain.cart.controller;

import java.util.List;

import com.springboot.bizconnect.domain.cart.dto.list.SupplierListRequestDto;
import com.springboot.bizconnect.domain.cart.dto.list.SupplierListResponseDto;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.cart.dto.detail.CompanyProductDetailResponseDto;
import com.springboot.bizconnect.domain.cart.service.CartService;
import com.springboot.bizconnect.domain.product.dto.list.ProductListRequestDto;
import com.springboot.bizconnect.domain.product.dto.list.ProductListResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
@Tag(name = "BuyerCart", description = "구매사 장바구니 관련 API")
public class BuyerCartController {
	private final CartService cartService;
	
	/*
	 * 해당 회사의 상품 리스트 조회(조건 : 해당 회사의 제품번호 즉 company_product_cart에 등록이 되어 있어야함, 사용유무가 true여야)
	 * 상품 상세 조회(앞선 조건과 마찬가지)
	 */

	@GetMapping("/suppliers")
	@Operation(summary = "거래중인 공급사 목록", description = "내 회사에 상품을 배정한 공급사 목록을 조회합니다.")
	public ResponseEntity<List<SupplierListResponseDto>> getSupplierList(
			@AuthenticationPrincipal CustomUserDetails userDetails,
			@ParameterObject SupplierListRequestDto requestDto
	) {
		List<SupplierListResponseDto> responseDto = cartService.getSupplierList(userDetails, requestDto);
		return ResponseEntity.ok(responseDto);
	}

	@GetMapping("/suppliers/{supplierCompanyNo}/products")
	@Operation(summary = "공급사 상품 리스트 조회", description = "특정 공급사가 배정한 상품 목록을 조회합니다.")
	public ResponseEntity<List<ProductListResponseDto>> getCartProductList(
			@AuthenticationPrincipal CustomUserDetails userDetails,
			@PathVariable Long supplierCompanyNo,
			@ParameterObject ProductListRequestDto requestDto
	) {
		List<ProductListResponseDto> responseDto = cartService.getCartProductList(userDetails, supplierCompanyNo, requestDto);
		return ResponseEntity.ok(responseDto);
	}

	@GetMapping("/suppliers/{supplierCompanyNo}/products/{productNo}")
	@Operation(summary = "상품 상세 조회", description = "특정 상품의 상세 정보를 조회합니다.")
	public ResponseEntity<CompanyProductDetailResponseDto> getCartProductDetail(
			@AuthenticationPrincipal CustomUserDetails userDetails,
			@PathVariable Long supplierCompanyNo,
			@PathVariable Long productNo
	) {
		CompanyProductDetailResponseDto responseDto = cartService.getCartProductDetail(userDetails, supplierCompanyNo, productNo);
		return ResponseEntity.ok(responseDto);
	}

	// 거래 중인 공급사 목록 조회 추가

}
