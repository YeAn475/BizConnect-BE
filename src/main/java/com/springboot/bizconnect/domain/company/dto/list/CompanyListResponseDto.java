package com.springboot.bizconnect.domain.company.dto.list;

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
public class CompanyListResponseDto {
    private Long companyNo;
    private String companyName;
    private String affiliationName;
    private String branchName;
    private String address;
    private String phoneNumber;
}
