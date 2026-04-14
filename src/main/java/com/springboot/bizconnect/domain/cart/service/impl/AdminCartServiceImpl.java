package com.springboot.bizconnect.domain.cart.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.springboot.bizconnect.domain.cart.dto.list.CompanyProductListRequestDto;
import com.springboot.bizconnect.domain.cart.dto.list.CompanyProductListResponseDto;
import com.springboot.bizconnect.domain.cart.dto.update.UpdateCartProductRequestDto;
import com.springboot.bizconnect.domain.cart.dto.update.UpdateCartProductResponseDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.springboot.bizconnect.domain.alarm.service.AlarmService;
import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.cart.dto.assign.AssignProductRequestDto;
import com.springboot.bizconnect.domain.cart.dto.assign.AssignProductResponseDto;
import com.springboot.bizconnect.domain.cart.dto.list.ProductAdminCartListRequestDto;
import com.springboot.bizconnect.domain.cart.dto.list.ProductAdminCartListResponseDto;
import com.springboot.bizconnect.domain.cart.repository.CompanyProductCartRepository;
import com.springboot.bizconnect.domain.cart.service.AdminCartService;
import com.springboot.bizconnect.domain.company.repository.CompanyRepository;
import com.springboot.bizconnect.domain.product.repository.ProductRepository;
import com.springboot.bizconnect.entity.Company;
import com.springboot.bizconnect.entity.CompanyProductCart;
import com.springboot.bizconnect.entity.Product;
import com.springboot.bizconnect.enums.AlarmType;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminCartServiceImpl implements AdminCartService{
	
	private final CompanyProductCartRepository companyProductCartRepository;
    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;
    private final AlarmService alarmService;
   
    
	@Override
	public AssignProductResponseDto assignProduct(CustomUserDetails userDetails, AssignProductRequestDto requestDto) {
		if (userDetails.getUser().getRole().getRoleNo() != 3L) {
	        throw new RuntimeException("해당 기능을 수행할 권한이 없습니다. (관리자 전용)");
	    }
		
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


	    // 해당 회사에 cart추가 됐다고 확인 알람 전송로직
        String alarmTitle = "신규 상품 배정 알림";
        String alarmContent = String.format("[%s] 외 %d건의 상품이 배정되었습니다. 장바구니를 확인해 주세요.", 
                                            productNamesList.get(0), productNamesList.size() - 1);
        
        // 작성하신 sendToCompanyMembers 활용
        alarmService.sendToCompanyMembers(
        	    userDetails.getUser(),        // 1. .getUserNo()를 빼고 User 객체 자체를 전달
        	    requestDto.getCompanyNo(),   // 2. 수신 회사 번호
        	    alarmTitle,                  // 3. 제목
        	    alarmContent,                // 4. 내용
        	    AlarmType.GENERAL            // 5. 누락됐던 알람 타입 추가
        );
        
       
        companyProductCartRepository.saveAll(cartEntities);

	    return AssignProductResponseDto.builder()
	            .productName(productNamesList)
	            .build();
	}

	@Override
	public List<ProductAdminCartListResponseDto> getCompanyCartList(CustomUserDetails userDetails, ProductAdminCartListRequestDto requestDto) {
		if (userDetails.getUser().getRole().getRoleNo() != 3L) {
	        throw new RuntimeException("해당 기능을 수행할 권한이 없습니다. (관리자 전용)");
	    }
		PageRequest pageRequest = PageRequest.of(requestDto.getPage(), requestDto.getSize());

	    return companyProductCartRepository.findDistinctCompaniesWithUsedCart(pageRequest)
	            .map(company -> ProductAdminCartListResponseDto.builder()
	                    .companyNo(company.getCompanyNo())
	                    .companyName(company.getName())
	                    .build())
	            .getContent();
				
	}

	@Override
	public List<CompanyProductListResponseDto> getCompanyCart(CustomUserDetails userDetails, Long companyNo, CompanyProductListRequestDto requestDto) {
		if (userDetails.getUser().getRole().getRoleNo() != 3L) {
			throw new RuntimeException("해당 기능을 수행할 권한이 없습니다. (관리자 전용)");
		}

		PageRequest pageRequest = PageRequest.of(requestDto.getPage(), requestDto.getSize());

		return companyProductCartRepository.findByNo_CompanyNo(companyNo, pageRequest)
				.map(companyProductCart -> CompanyProductListResponseDto.builder()
						.productNo(companyProductCart.getProduct().getProductNo())
						.name(companyProductCart.getProduct().getName())
						.imageUrl(companyProductCart.getProduct().getImageUrl())
						.isUsed(companyProductCart.getIsUsed())
						.build())
				.getContent();
	}

	@Override
	public UpdateCartProductResponseDto UpdateCompanyProductCart(CustomUserDetails userDetails, UpdateCartProductRequestDto requestDto) {
		if (userDetails.getUser().getRole().getRoleNo() != 3L) {
			throw new RuntimeException("해당 기능을 수행할 권한이 없습니다. (관리자 전용)");
		}

		int updatedCount = 0;

		for (Long productNo : requestDto.getProductNos()) {
			CompanyProductCart cart = companyProductCartRepository
					.findByNo_CompanyNoAndNo_ProductNo(requestDto.getCompanyNo(), productNo)
					.orElseThrow(() -> new RuntimeException("해당 상품이 장바구니에 존재하지 않습니다: " + productNo));

			// isUsed 토글
			cart.setIsUsed(!cart.getIsUsed());
			companyProductCartRepository.save(cart);
			updatedCount++;
		}

		return new UpdateCartProductResponseDto(updatedCount + "개 상품의 사용 여부가 변경되었습니다.");
	}
}
