package com.springboot.bizconnect.domain.cart.service.impl;

import java.util.List;

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
	
	private final CompanyProductCartRepository companyproductcartRepository;
	private final ProductRepository productRepository;

	@Override
	public List<ProductListResponseDto> getCartProductList(CustomUserDetails userDetails, ProductListRequestDto requestDto) {
		Long companyNo = userDetails.getCompanyNo();
		
		PageRequest pageRequest = PageRequest.of(requestDto.getPage(), requestDto.getSize());
		
		return companyproductcartRepository.findAllByCompanyNoAndIsUsed(companyNo, pageRequest)
                .map(product -> ProductListResponseDto.builder()
                        .productNo(product.getProductNo())
                        .name(product.getName())
                        .imageUrl(product.getImageUrl())
                        .build())
                .getContent();
	}

	@Override
	public CompanyProductDetailResponseDto getCartProductDetail(CompanyProductDetailRequestDto requestDto,
			CustomUserDetails userDetails) {
		Long companyNo = userDetails.getCompanyNo();

	    boolean isMyCompanyProduct = companyproductcartRepository
	            .existsById_CompanyNoAndId_ProductNoAndIsUsedTrue(companyNo, requestDto.getProductNo());

	    if (!isMyCompanyProduct) {
	        throw new RuntimeException("해당 상품에 대한 접근 권한이 없거나 존재하지 않습니다.");
	    }

	    Product product = productRepository.findById(requestDto.getProductNo())
	            .orElseThrow(() -> new RuntimeException("상품 정보를 찾을 수 없습니다."));

	    return CompanyProductDetailResponseDto.builder()
	            .productNo(product.getProductNo())
	            .name(product.getName())
	            .content(product.getContent())
	            .price(product.getPrice())
	            .imageUrl(product.getImageUrl())
	            .unitName(product.getUnit().getName())
	            .categoryName(product.getCategory().getName())
	            .manufacturerName(product.getManufacturer().getName())
	            .productStatusName(product.getProductStatus().getName())
	            .createdAt(product.getCreatedAt())
	            .updatedAt(product.getUpdatedAt())
	            .build();
	
	}

}
