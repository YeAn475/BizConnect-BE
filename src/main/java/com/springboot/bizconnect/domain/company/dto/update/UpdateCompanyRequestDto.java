package com.springboot.bizconnect.domain.company.dto.update;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCompanyRequestDto {
    private String companyName;
    private String branchName;
    private String affiliationName;
    private String companyAddress;
    private String companyPhone;
}