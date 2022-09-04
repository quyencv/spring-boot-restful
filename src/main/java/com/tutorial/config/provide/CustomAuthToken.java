package com.tutorial.config.provide;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.tutorial.dto.UserDTO;

import lombok.Getter;

public class CustomAuthToken extends UsernamePasswordAuthenticationToken {

    private static final long serialVersionUID = 1279545870000468363L;

    @Getter
    private UserDTO userDTO;

    public CustomAuthToken(Object principal, Object credentials, UserDTO userDTO) {
        super(principal, credentials);
        this.userDTO = userDTO;
    }

    public CustomAuthToken(Object principal, Object credentials, UserDTO userDTO,
            Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
        this.userDTO = userDTO;
    }

}