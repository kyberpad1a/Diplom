package com.example.diplom.model;

import org.springframework.security.core.GrantedAuthority;

public enum roleEnum implements GrantedAuthority {
    USER, ADMIN, HR;
    @Override
    public String getAuthority()
    {
        return name();
    }
}