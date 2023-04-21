package com.capstone.project.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {

    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"),
    DELIVERY("ROLE_DELIVERY");

    private final String key;
}
