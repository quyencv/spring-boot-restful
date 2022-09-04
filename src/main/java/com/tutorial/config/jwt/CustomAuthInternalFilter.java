package com.tutorial.config.jwt;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tutorial.config.provide.CustomAuthToken;
import com.tutorial.constant.Role;
import com.tutorial.dto.UserDTO;
import com.tutorial.utils.JsonUtils;

@Component
public class CustomAuthInternalFilter extends OncePerRequestFilter {
    private final Logger logger = LoggerFactory.getLogger(CustomAuthInternalFilter.class);

    private String clientId = "clientId";

    private String secretKey = "secretKey";

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getRequestURI().startsWith("/api/internal");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String url = request.getRequestURL().toString();
        String queryString = request.getQueryString();
        logger.debug("CustomAuthInternalFilter doFilterInternal URL: {}?{}", url, queryString);

        Optional<UserDTO> userDTOOpt = JsonUtils.servletRequestToObject(request, UserDTO.class);
        if (userDTOOpt.isPresent() && clientId.equals(userDTOOpt.get().getClientId())
                && secretKey.equals(userDTOOpt.get().getSecretKey())) {
            Set<GrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority("ROLE_" + Role.SYSTEM_MANAGER.name()));
            Authentication authentication = new CustomAuthToken(userDTOOpt.get().getClientId(), null, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            logger.debug("CustomAuthInternalFilter doFilterInternal clientId or secretKey not match.");
        }

        filterChain.doFilter(request, response);
    }
}
