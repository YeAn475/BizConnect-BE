package com.springboot.bizconnect.domain.cart.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.springboot.bizconnect.entity.Company;
import com.springboot.bizconnect.entity.CompanyProductCart;
import com.springboot.bizconnect.entity.Product;
import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.cart.dto.assign.AssignProductRequestDto;
import com.springboot.bizconnect.domain.cart.dto.assign.AssignProductResponseDto;
import com.springboot.bizconnect.domain.cart.repository.CompanyProductCartRepository;
import com.springboot.bizconnect.domain.cart.service.AdminCartService;
import com.springboot.bizconnect.domain.company.repository.CompanyRepository;
import com.springboot.bizconnect.domain.product.repository.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminCartServiceImpl implements AdminCartService{
	
	private final CompanyProductCartRepository companyProductCartRepository;
    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;
    
	@Override
	public AssignProductResponseDto assignProduct(CustomUserDetails userDetails, AssignProductRequestDto requestDto) {
		Company company = companyRepository.findById(requestDto.getCompanyNo())
	            .orElseThrow(() -> new RuntimeException("존재하지 않는 회사입니다."));

	    List<CompanyProductCart> cartEntities = new ArrayList<>();
	    List<String> productNamesList = new ArrayList<>(); // 변수명 소문자로 권장

	    for (Long pNo : requestDto.getProductNo()) {
	        Product product = productRepository.findById(pNo)
	                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));

	        Long statusNo = product.getProductStatus().getProductStatusNo();
	        if (statusNo == 3 || statusNo == 5) {
	            throw new RuntimeException(product.getName() + "은(는) 배정할 수 없는 상품입니다. (단종 또는 비공개)");
	        }

	        CompanyProductCart cart = CompanyProductCart.builder()
	                .no(new CompanyProductCart.CompanyProductNo(requestDto.getCompanyNo(), pNo))
	                .company(company)
	                .product(product)
	                .isUsed(true)
	                .build();

	        cartEntities.add(cart);
	        productNamesList.add(product.getName()); // 리스트에 이름 추가
	    }

	    companyProductCartRepository.saveAll(cartEntities);


	    return AssignProductResponseDto.builder()
	            .productName(productNamesList)
	            .build();
	}
	
	
	

}
