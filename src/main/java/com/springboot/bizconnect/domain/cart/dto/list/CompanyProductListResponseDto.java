package com.springboot.bizconnect.domain.cart.dto.list;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyProductListResponseDto {
    private Long productNo;
    private String name;
    private String imageUrl;
}
