package com.capstone.project.login.handler;

import com.capstone.project.domain.constants.AuthConstants;
import com.capstone.project.domain.dto.SuccessLoginMemberDto;
import com.capstone.project.domain.dto.Token;
import com.capstone.project.domain.entity.Member;
import com.capstone.project.domain.entity.MyUserDetails;
import com.capstone.project.domain.enums.UserRole;
import com.capstone.project.utils.TokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
public class CustomFormLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final TokenUtils tokenUtils;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        final  Member member = ((MyUserDetails) authentication.getPrincipal()).getMember();
        final Token token = tokenUtils.generateToken(member.getEmail(), UserRole.USER.getKey());

        // response에 accessToken을 넣을지 말지 결정해야함
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader(AuthConstants.AUTH_HEADER, token.getAccessToken());
        response.setHeader(AuthConstants.REFRESH_HEADER, token.getRefreshToken());

        final SuccessLoginMemberDto successLoginMemberDto = new SuccessLoginMemberDto();
        successLoginMemberDto.setName(member.getName());
        successLoginMemberDto.setAccessToken(token.getAccessToken());

        String loginMemberJsonResponse = objectMapper.writeValueAsString(successLoginMemberDto);
        response.getWriter().write(loginMemberJsonResponse);

    }
}
