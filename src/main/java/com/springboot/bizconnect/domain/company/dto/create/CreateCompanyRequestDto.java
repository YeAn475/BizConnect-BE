package com.springboot.bizconnect.domain.company.dto.create;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCompanyRequestDto {
    @Schema(description = "이름")
    private String companyName;
    @Schema(description = "소속명")
    private String affiliationName;
    @Schema(description = "브랜드명")
    private String branchName;
    @Schema(description = "주소")
    private String address;
    @Schema(description = "회사번호")
    private String phoneNumber;
    @Schema(description = "설명")
    private String content;
}
