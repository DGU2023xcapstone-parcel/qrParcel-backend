package com.capstone.project.service;

import com.capstone.project.domain.dto.MemberDto;
import com.capstone.project.domain.dto.SignUpDto;
import com.capstone.project.domain.entity.Member;
import com.capstone.project.domain.enums.UserRole;
import com.capstone.project.repository.MemberRepository;
import com.capstone.project.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService {

    private final TokenUtils tokenUtils;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Boolean isEmailDuplicated(final String email){
        return memberRepository.existsByEmail(email);
    }

    public Member findByEmail(String email){
        return memberRepository.findByEmail(email).orElse(null);
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
}
