package com.springboot.bizconnect.domain.product.service.impl;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.product.dto.create.CreateProductRequestDto;
import com.springboot.bizconnect.domain.product.dto.create.CreateProductResponseDto;
import com.springboot.bizconnect.domain.product.dto.list.ProductListRequestDto;
import com.springboot.bizconnect.domain.product.dto.list.ProductListResponseDto;
import com.springboot.bizconnect.domain.product.repository.CategoryRepository;
import com.springboot.bizconnect.domain.product.repository.ManufacturerRepository;
import com.springboot.bizconnect.domain.product.repository.ProductRepository;
import com.springboot.bizconnect.domain.product.repository.ProductStatusRepository;
import com.springboot.bizconnect.domain.product.repository.UnitRepository;
import com.springboot.bizconnect.domain.product.service.ProductService;
import com.springboot.bizconnect.domain.user.repository.UserRepository;
import com.springboot.bizconnect.entity.Category;
import com.springboot.bizconnect.entity.Manufacturer;
import com.springboot.bizconnect.entity.Product;
import com.springboot.bizconnect.entity.ProductStatus;
import com.springboot.bizconnect.entity.Unit;
import com.springboot.bizconnect.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.service.GenericResponseService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final UserRepository userRepository;
    private final UnitRepository unitRepository;
    private final CategoryRepository categoryRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final ProductStatusRepository productStatusRepository;
    private final ProductRepository productRepository;
    private final GenericResponseService responseBuilder;

    @Override
    public CreateProductResponseDto createProduct(CustomUserDetails userDetails, CreateProductRequestDto requestDto) {
        // 권한 체크
        User user = userRepository.findById(userDetails.getUser().getUserNo())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        Long userRoleNo = user.getRole().getRoleNo();
        if (!Arrays.asList(3L, 4L).contains(userRoleNo)) {
            throw new RuntimeException("권한이 없습니다.");
        }

        // 이름으로 각 마스터 데이터 조회 → no 매핑
        Unit unit = unitRepository.findByName(requestDto.getUnit())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 단위입니다: " + requestDto.getUnit()));

        Category category = categoryRepository.findByName(requestDto.getCategory())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 카테고리입니다: " + requestDto.getCategory()));

        Manufacturer manufacturer = manufacturerRepository.findByName(requestDto.getManufacturer())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 제조사입니다: " + requestDto.getManufacturer()));

        ProductStatus productStatus = productStatusRepository.findByName(requestDto.getProductStatus())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 상품 상태입니다: " + requestDto.getProductStatus()));

        // 상품 등록
        Product product = Product.builder()
                .unit(unit)
                .category(category)
                .manufacturer(manufacturer)
                .productStatus(productStatus)
                .name(requestDto.getName())
                .content(requestDto.getContent())
                .price(requestDto.getPrice())
                .imageUrl(requestDto.getImageUrl())
                .build();

        productRepository.save(product);

        return new CreateProductResponseDto(requestDto.getName() + " 상품이 등록 완료되었습니다.");
    }

	@Override
	public List<ProductListResponseDto> getProductList(ProductListRequestDto requestDto) {
		PageRequest pageRequest = PageRequest.of(requestDto.getPage(), requestDto.getSize());
		
		return productRepository.findAll(pageRequest)
				.map(product -> ProductListResponseDto.builder()
						.productNo(product.getProductNo())
						.name(product.getName())
						.imageUrl(product.getImageUrl()).build())
				.getContent();
	}
}
