package com.capstone.project.domain.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthConstants {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTH_HEADER = "AccessToken";
    public static final String REFRESH_HEADER = "RefreshToken";
    public static final String TOKEN_TYPE = "Bearer";

}
