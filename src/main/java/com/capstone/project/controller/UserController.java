package com.capstone.project.controller;

import com.capstone.project.domain.dto.MemberDto;
import com.capstone.project.domain.dto.ParcelDto;
import com.capstone.project.domain.dto.SignUpDto;
import com.capstone.project.domain.entity.Member;
import com.capstone.project.domain.entity.TrackingInfo;
import com.capstone.project.service.MemberService;
import com.capstone.project.utils.TokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "사용자")
@RequiredArgsConstructor
@Log4j2
@RestController
@RequestMapping("/user")
public class UserController {

    private final MemberService memberService;
    private TokenUtils tokenUtils;

    @ApiOperation(value = "회원가입")
    @PostMapping("/signUp")
    public ResponseEntity<MemberDto> signUp(@RequestBody SignUpDto signUpDto, HttpServletRequest request, HttpServletResponse response) throws IOException {

        log.info("사용자 이름 : {}", signUpDto.getName());
        log.info("사용자 이메일 : {}", signUpDto.getEmail());
        log.info("사용자 비밀번호 : {}", signUpDto.getPassword());

        if(memberService.isEmailDuplicated(signUpDto.getEmail())){
            log.error("이미 존재하는 회원 이메일입니다.");
            return ResponseEntity.badRequest().build();
        } else{
            MemberDto memberDto = memberService.signUp(signUpDto);
            ResponseEntity.status(200);
            return ResponseEntity.ok(memberDto);
        }
    }

    @ApiOperation(value = "택배 리스트")
    @GetMapping("/parcels")
    public Result getParcelList(HttpServletRequest request, HttpServletResponse response) {
        Member member = memberService.getMemberThroughRequest(request);
        List<ParcelDto> memberParcels = member.getParcels().stream()
                .map(t -> ParcelDto.builder()
                        .invoiceNo(t.getInvoiceNo())
                        .productName(t.getProductName())
                        .receiverAddress(t.getReceiverAddress())
                        .sender(t.getSender())
                        .build())
                .collect(Collectors.toList());

        return new Result(memberParcels.size(), memberParcels);
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private int count;
        private T data;
    }
}
