package com.capstone.project.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SignUpDto {

    @ApiModelProperty(value = "회원 이름", dataType = "string", required = true)
    private String name;

    @ApiModelProperty(value = "회원 이메일")
    private String email;

    @ApiModelProperty(value = "회원 비밀번")
    private String password;

}
