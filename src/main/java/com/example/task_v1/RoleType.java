package com.example.task_v1;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;

public enum RoleType {ROLE_USER, ROLE_MANAGER;

    @JsonCreator
    public static RoleType forValue(String value) {
        return Arrays.stream(RoleType.values())
                .filter(role -> role.name().equalsIgnoreCase(value))
                .findFirst()
                .orElse(null);
    }
}
