package com.springboot.bizconnect.domain.friend.dto.search;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendSearchRequestDto {
    @Schema(description = "검색어 (이름)")
    private String keyword;

    @Schema(description = "페이지 번호", defaultValue = "0")
    private Integer page = 0;

    @Schema(description = "페이지 크기", defaultValue = "10")
    private Integer size = 10;
}