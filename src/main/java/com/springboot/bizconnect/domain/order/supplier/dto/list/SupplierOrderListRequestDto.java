package com.springboot.bizconnect.domain.order.supplier.dto.list;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierOrderListRequestDto {
    @Schema(description = "페이지 번호", defaultValue = "0")
    private Integer page = 0;

    @Schema(description = "페이지 크기", defaultValue = "10")
    private Integer size = 10;
}
