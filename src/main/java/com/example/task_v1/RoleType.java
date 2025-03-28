package com.example.task_v1;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum RoleType {ROLE_USER, ROLE_MANAGER;

    public GrantedAuthority toAuthority() {
        return new SimpleGrantedAuthority(this.name());
    }
}
