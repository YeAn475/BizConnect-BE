package com.springboot.bizconnect.domain.cart.service.impl;

import java.util.List;

import com.springboot.bizconnect.domain.cart.dto.list.SupplierListRequestDto;
import com.springboot.bizconnect.domain.cart.dto.list.SupplierListResponseDto;
import com.springboot.bizconnect.entity.CompanyProductCart;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.cart.dto.detail.CompanyProductDetailRequestDto;
import com.springboot.bizconnect.domain.cart.dto.detail.CompanyProductDetailResponseDto;
import com.springboot.bizconnect.domain.cart.repository.CompanyProductCartRepository;
import com.springboot.bizconnect.domain.cart.service.CartService;
import com.springboot.bizconnect.domain.product.dto.list.ProductListRequestDto;
import com.springboot.bizconnect.domain.product.dto.list.ProductListResponseDto;
import com.springboot.bizconnect.domain.product.repository.ProductRepository;
import com.springboot.bizconnect.entity.Product;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{

	private final CompanyProductCartRepository companyProductCartRepository;

	@Override
	public List<SupplierListResponseDto> getSupplierList(CustomUserDetails userDetails, SupplierListRequestDto requestDto) {
		Long buyerCompanyNo = userDetails.getUser().getCompany().getCompanyNo();
		PageRequest pageRequest = PageRequest.of(requestDto.getPage(), requestDto.getSize());

		return companyProductCartRepository.findDistinctSuppliersByBuyer(buyerCompanyNo, pageRequest)
				.map(company -> SupplierListResponseDto.builder()
						.supplierCompanyNo(company.getCompanyNo())
						.supplierCompanyName(company.getName())
						.build())
				.getContent();
	}

	@Override
	public List<ProductListResponseDto> getCartProductList(CustomUserDetails userDetails, Long supplierCompanyNo, ProductListRequestDto requestDto) {
		Long buyerCompanyNo = userDetails.getUser().getCompany().getCompanyNo();
		PageRequest pageRequest = PageRequest.of(requestDto.getPage(), requestDto.getSize());

		return companyProductCartRepository.findByNo_SupplierCompanyNoAndNo_BuyerCompanyNoAndIsUsedTrue(supplierCompanyNo, buyerCompanyNo, pageRequest)
				.map(cart -> ProductListResponseDto.builder()
						.productNo(cart.getProduct().getProductNo())
						.name(cart.getProduct().getName())
						.imageUrl(cart.getProduct().getImageUrl())
						.build())
				.getContent();
	}

	@Override
	public CompanyProductDetailResponseDto getCartProductDetail(CustomUserDetails userDetails, Long supplierCompanyNo, Long productNo) {
		Long buyerCompanyNo = userDetails.getUser().getCompany().getCompanyNo();

		CompanyProductCart cart = companyProductCartRepository
				.findByNo_SupplierCompanyNoAndNo_BuyerCompanyNoAndNo_ProductNo(supplierCompanyNo, buyerCompanyNo, productNo)
				.orElseThrow(() -> new RuntimeException("해당 상품이 존재하지 않습니다."));

		if (!cart.getIsUsed()) {
			throw new RuntimeException("현재 주문할 수 없는 상품입니다.");
		}

		Product product = cart.getProduct();

		return CompanyProductDetailResponseDto.builder()
				.productNo(product.getProductNo())
				.name(product.getName())
				.content(product.getContent())
				.price(product.getPrice())
				.imageUrl(product.getImageUrl())
				.unitName(product.getUnit().getName())
				.categoryName(product.getCategory().getName())
				.manufacturerName(product.getManufacturer().getName())
				.build();
	}

}
