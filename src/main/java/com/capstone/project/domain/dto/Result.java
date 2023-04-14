package com.capstone.project.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result<T>{

    @Schema(description = "반환 개수", nullable = false, example = "24")
    private int count;

    @Schema(description = "조회 상품", nullable = false, example = "")
    private T data;

}
