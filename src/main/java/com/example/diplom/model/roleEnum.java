package com.example.diplom.model;

import org.springframework.security.core.GrantedAuthority;

public enum roleEnum implements GrantedAuthority {
    USER, COURIER, ADMIN, GOODSSTAFF;
    @Override
    public String getAuthority()
    {
        return name();
    }
}