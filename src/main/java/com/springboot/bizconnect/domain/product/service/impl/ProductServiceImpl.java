package com.springboot.bizconnect.domain.product.service.impl;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.image.CloudinaryService;
import com.springboot.bizconnect.domain.product.dto.create.CreateProductRequestDto;
import com.springboot.bizconnect.domain.product.dto.create.CreateProductResponseDto;
import com.springboot.bizconnect.domain.product.dto.detail.ProductDetailRequestDto;
import com.springboot.bizconnect.domain.product.dto.detail.ProductDetailResponseDto;
import com.springboot.bizconnect.domain.product.dto.image.ProductImageResponseDto;
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

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    private final CloudinaryService cloudinaryService;

    @Override
    public CreateProductResponseDto createProduct(CustomUserDetails userDetails, CreateProductRequestDto requestDto) {
        // 권한 체크
        User user = userRepository.findById(userDetails.getUser().getUserNo())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        Long userRoleNo = user.getRole().getRoleNo();
        if (!Arrays.asList(3L, 4L).contains(userRoleNo)) {
            throw new RuntimeException("권한이 없습니다.");
        }

        // 이름으로 각 마스터 데이터 조회
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

	@Override
	public ProductDetailResponseDto ProductDetail(ProductDetailRequestDto requestDto) {
		Product product = productRepository.findById(requestDto.getProductNo())
				.orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));
		
		return ProductDetailResponseDto.builder()
				.productNo(product.getProductNo())
				.unitName(product.getUnit().getName())
				.categoryName(product.getCategory().getName())
				.manufacturerName(product.getManufacturer().getName())
				.productStatusName(product.getProductStatus().getName())
				.name(product.getName())
				.content(product.getContent())
				.price(product.getPrice())
				.imageUrl(product.getImageUrl())
				.createdAt(product.getCreatedAt())
				.updatedAt(product.getUpdatedAt())
				.build();
				
	}

	@Override
	@Transactional
	public ProductImageResponseDto uploadProductImage(Long productNo, MultipartFile image) {
		// 1. 상품 존재 확인
	    Product product = productRepository.findById(productNo)
	            .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));

	    // 2. 기존 이미지가 있으면 Cloudinary에서 삭제
	    if (product.getImageUrl() != null && !product.getImageUrl().isEmpty()) {
	        cloudinaryService.delete(product.getImageUrl());
	    }

	    // 3. 새 이미지 업로드 (폴더명을 "product"로 지정)
	    String imageUrl = cloudinaryService.upload(image, "product");

	    // 4. DB에 URL 저장 (Dirty Checking으로 자동 업데이트)
	    product.setImageUrl(imageUrl);

	    return ProductImageResponseDto.builder()
	            .message("상품 이미지가 성공적으로 등록되었습니다.")
	            .url(imageUrl)
	            .build();
	}
}
