package com.springboot.bizconnect.domain.cart.controller;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.cart.dto.detail.CompanyProductDetailResponseDto;
import com.springboot.bizconnect.domain.cart.dto.detail.CompanyProductDetailRequestDto;
import com.springboot.bizconnect.domain.cart.service.CartService;
import com.springboot.bizconnect.domain.product.dto.list.ProductListRequestDto;
import com.springboot.bizconnect.domain.product.dto.list.ProductListResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
@Tag(name = "Cart", description = "장바구니 관련 API")
public class CartController {
	private final CartService cartService;
	
	/*
	 * 해당 회사의 상품 리스트 조회(조건 : 해당 회사의 제품번호 즉 company_product_cart에 등록이 되어 있어야함, 사용유무가 true여야)
	 * 상품 상세 조회(앞선 조건과 마찬가지)
	 */
	
	@PostMapping("/list")
    @Operation(summary = "회사 상품 리스트 조회", description = "회사의 상품 리스트를 조회합니다.")
    public ResponseEntity<List<ProductListResponseDto>> getCartProductList(
    		@AuthenticationPrincipal CustomUserDetails userDetails,
    		@ParameterObject ProductListRequestDto requestDto
    ) {
    	List<ProductListResponseDto> responseDto = cartService.getCartProductList(userDetails, requestDto);
    	return ResponseEntity.ok(responseDto);
    }
	
	@GetMapping("/{productNo}")
	@Operation(summary = "회사 상품 상세 조회", description = "회사의 상품을 조회합니다.")
	public ResponseEntity<CompanyProductDetailResponseDto> getCartProductDetail(
			@ParameterObject CompanyProductDetailRequestDto requestDto,
	        @AuthenticationPrincipal CustomUserDetails userDetails) {
	    CompanyProductDetailResponseDto responseDto = cartService.getCartProductDetail(requestDto, userDetails);
		
	    // 서비스 호출 시 productNo와 userDetails를 넘겨 보안 검증과 조회를 동시에 처리
	    return ResponseEntity.ok(responseDto);
	}
}
