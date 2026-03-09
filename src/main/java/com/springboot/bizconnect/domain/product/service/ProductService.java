package com.springboot.bizconnect.domain.product.service;

import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.product.dto.create.CreateProductRequestDto;
import com.springboot.bizconnect.domain.product.dto.create.CreateProductResponseDto;
import com.springboot.bizconnect.domain.product.dto.detail.ProductDetailRequestDto;
import com.springboot.bizconnect.domain.product.dto.detail.ProductDetailResponseDto;
import com.springboot.bizconnect.domain.product.dto.image.ProductImageResponseDto;
import com.springboot.bizconnect.domain.product.dto.list.ProductListRequestDto;
import com.springboot.bizconnect.domain.product.dto.list.ProductListResponseDto;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    CreateProductResponseDto createProduct(@AuthenticationPrincipal CustomUserDetails userDetails, @ParameterObject CreateProductRequestDto requestDto);
    
    List<ProductListResponseDto> getProductList(@ParameterObject ProductListRequestDto requestDto);
    
    ProductDetailResponseDto ProductDetail(@ParameterObject ProductDetailRequestDto requestDto);
    
    ProductImageResponseDto uploadProductImage(@PathVariable("productNo") Long productNo,@RequestPart("image") MultipartFile image);
}
