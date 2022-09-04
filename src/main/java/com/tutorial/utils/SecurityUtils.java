package com.tutorial.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static Optional<Authentication> getCurrentAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Optional.ofNullable(authentication);
    }

    public static String getCurrentUser() {
        return getCurrentAuthentication().map(auth -> auth.getName()).orElse(StringUtils.EMPTY);
    }

    public static List<String> getListRole() {
        return getCurrentAuthentication().map(auth -> auth.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .distinct().collect(Collectors.toList())).orElse(Collections.emptyList());
    }

    public static boolean hasRole(String role) {
        if (StringUtils.isEmpty(role)) {
            return false;
        }
        return hasAnyRole(role);
    }

    public static boolean hasAnyRole(String... roles) {
        if (ArrayUtils.isEmpty(roles)) {
            return false;
        }

        return getListRole().stream()
                .anyMatch(authRole -> Arrays.stream(roles).anyMatch(role -> authRole.equalsIgnoreCase(role)));
    }

    public static boolean isAuthenticated() {
        // return getAuthorities().stream().noneMatch(AuthoritiesConstant.ANONYMOUS::equals);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return Objects.nonNull(auth) && !auth.getClass().equals(AnonymousAuthenticationToken.class);
    }

    public static boolean hasAuthority(String authority) {
        return getAuthorities().stream().anyMatch(authority::equals);
    }

    public static List<String> getAuthorities() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Objects.isNull(authentication) ? Collections.emptyList()
                : authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList());
    }
}
