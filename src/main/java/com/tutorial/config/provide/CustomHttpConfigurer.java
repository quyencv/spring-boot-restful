package com.tutorial.config.provide;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import com.tutorial.config.jwt.CustomAuthProcessingFilter;
import com.tutorial.constant.UrlPathConstant;
import com.tutorial.utils.JsonUtils;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CustomHttpConfigurer extends AbstractHttpConfigurer<CustomHttpConfigurer, HttpSecurity> {

    private final AuthenticationSuccessHandler authenticationSuccessHandler;

    private final AuthenticationFailureHandler authenticationFailureHandler;
    
    private final JsonUtils jsonUtils;

    private static final RequestMatcher PROTECTED_URLS = new OrRequestMatcher(
            new AntPathRequestMatcher(UrlPathConstant.Authentication.SIGN_IN, HttpMethod.POST.name()));

    @Override
    public void configure(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        final CustomAuthProcessingFilter filter = new CustomAuthProcessingFilter(PROTECTED_URLS, authenticationManager, jsonUtils);
        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(authenticationFailureHandler);
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }

}
