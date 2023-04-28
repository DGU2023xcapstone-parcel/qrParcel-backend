package com.capstone.project.service;

import com.capstone.project.domain.constants.AuthConstants;
import com.capstone.project.domain.dto.MemberDto;
import com.capstone.project.domain.dto.SignUpDto;
import com.capstone.project.domain.entity.Member;
import com.capstone.project.domain.entity.TrackingInfo;
import com.capstone.project.domain.enums.UserRole;
import com.capstone.project.repository.MemberRepository;
import com.capstone.project.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService {

    private final TokenUtils tokenUtils;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private static final int PARSE_POS = 7;

    public Boolean isEmailDuplicated(final String email){
        return memberRepository.existsByEmail(email);
    }

    public Member findByEmail(String email){
        return memberRepository.findByEmail(email).orElse(null);
    }

    public Member findByPhoneNumber(String phoneNumber){
        return memberRepository.findByPhoneNumber(phoneNumber).orElse(null);
    }

    @Transactional
    public MemberDto signUp(final SignUpDto signUpDto){
        memberRepository.save(
                Member.builder()
                        .name(signUpDto.getName())
                        .email(signUpDto.getEmail())
                        .password(bCryptPasswordEncoder.encode(signUpDto.getPassword()))
                        .role(UserRole.USER)
                        .build());

        return MemberDto.builder()
                .name(signUpDto.getName())
                .email(signUpDto.getEmail())
                .build();
    }

    @Transactional
    public MemberDto adminSignUp(final SignUpDto signUpDto){
        memberRepository.save(
                Member.builder()
                        .name(signUpDto.getName())
                        .email(signUpDto.getEmail())
                        .password(bCryptPasswordEncoder.encode(signUpDto.getPassword()))
                        .role(UserRole.ADMIN)
                        .build());

        return MemberDto.builder()
                .name(signUpDto.getName())
                .email(signUpDto.getEmail())
                .build();
    }

    @Transactional
    public MemberDto deliverySignUp(final SignUpDto signUpDto){
        memberRepository.save(
                Member.builder()
                        .name(signUpDto.getName())
                        .email(signUpDto.getEmail())
                        .password(bCryptPasswordEncoder.encode(signUpDto.getPassword()))
                        .role(UserRole.DELIVERY)
                        .build());

        return MemberDto.builder()
                .name(signUpDto.getName())
                .email(signUpDto.getEmail())
                .build();
    }

    public void connectMemberToTracking(Member member, TrackingInfo trackingInfo){
        member.getParcels().add(trackingInfo);
    }

    public Member getMemberThroughRequest(HttpServletRequest request) {
        String author = request.getHeader(AuthConstants.AUTHORIZATION_HEADER);
        String token = author.substring(PARSE_POS, author.length());
        String email = tokenUtils.getUid(token);

        return this.findByEmail(email);
    }

    public Boolean isMatchPassword(String email, String password){
        Member member = memberRepository.findByEmail(email).orElse(null);
        if(member == null) return false;
        return bCryptPasswordEncoder.matches(member.getPassword(), password);
    }

    public Member getMember(String email){
        return memberRepository.findByEmail(email).orElse(null);
    }
}
