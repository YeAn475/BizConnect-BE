package com.springboot.bizconnect.domain.cart.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.bizconnect.domain.cart.service.AdminCartService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/cart")
@Tag(name = "AdminCart", description = "운영 관리자 장바구니 관련 API")
public class AdminCartController {
	private AdminCartService admincartservice;
	
	@PostMapping("/")
	@Operation(summary = "거래처 상품 추가", description = "운영 관리자가 거래처 장바구니에 상품들을 추가합니다.")
	public ResponseEntity<?> AddCompanyProductCart() {
		return null;
	}
	
}
