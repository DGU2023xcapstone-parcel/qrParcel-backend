package com.capstone.project.login.interceptor;

import com.capstone.project.domain.constants.AuthConstants;
import com.capstone.project.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log4j2
@RequiredArgsConstructor
public class JwtTokenInterceptor implements HandlerInterceptor {

    private final TokenUtils tokenUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String header = request.getHeader(AuthConstants.AUTHORIZATION_HEADER);
        if(header == null) return false;
        if(StringUtils.hasText(header) && header.startsWith(AuthConstants.TOKEN_TYPE)){
            String bearerToken = header.substring(7, header.length());
            log.info("BEARER TOKEN : " + bearerToken);
            if(tokenUtils.isValidToken(bearerToken)) return true;
        }

        /**
         * 웹에서는 쿠키에 refresh 담아서 accessToken이 만료되었을 경우 refresh가 유효한지 확인하고
         * 유효하다면 -> 재활성화시켜서 재발급 한 후 쿠키에 다시 담아서 반환시켰는데
         * 해당 프로젝트는 앱이기 때문에 쿠키를 사용하지 않음.
         *
         * 따라서 해당 파트를 어떻게 해결할 것인지 생각해봐야할 듯
         * **/

        response.sendRedirect("/error/unauthorized");
        return false;
    }
}
