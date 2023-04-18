package com.capstone.project.domain.dto;

import com.capstone.project.domain.entity.Address;
import com.capstone.project.domain.entity.Sender;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
//@ApiModel(value = "택배 모델")
public class InputParcelDto implements Serializable {


    @Schema(description = "운송장 번호", nullable = false, example = "015159831")
    private String invoiceNo;
    @Schema(description = "배송 제품 이름", nullable = false, example = "강아지 간식")
    private String productName;
    @Schema(description = "수령인 주소", nullable = false, example = "서울 중구 장충로")
    private Address receiverAddress;
    @Schema(description = "보내는 사람", nullable = false, example = "")
    private Sender sender;
    @Schema(description = "수령인 이름", nullable = false, example = "신현식")
    private String receiverName;
    @Schema(description = "수령인 전화번호", nullable = false, example = "010-4123-2691")
    private String receiverPhoneNum;


}
