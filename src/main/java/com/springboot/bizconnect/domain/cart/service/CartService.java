package com.springboot.bizconnect.domain.cart.service;

import java.util.List;

import com.springboot.bizconnect.domain.cart.dto.list.SupplierListRequestDto;
import com.springboot.bizconnect.domain.cart.dto.list.SupplierListResponseDto;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.cart.dto.detail.CompanyProductDetailRequestDto;
import com.springboot.bizconnect.domain.cart.dto.detail.CompanyProductDetailResponseDto;
import com.springboot.bizconnect.domain.product.dto.list.ProductListRequestDto;
import com.springboot.bizconnect.domain.product.dto.list.ProductListResponseDto;

public interface CartService {
	List<SupplierListResponseDto> getSupplierList(CustomUserDetails userDetails, SupplierListRequestDto requestDto);
	List<ProductListResponseDto> getCartProductList(CustomUserDetails userDetails, Long supplierCompanyNo, ProductListRequestDto requestDto);
	CompanyProductDetailResponseDto getCartProductDetail(CustomUserDetails userDetails, Long supplierCompanyNo, Long productNo);
}
