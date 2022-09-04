package com.tutorial.config.aware;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

import com.tutorial.utils.SecurityUtils;

public class CustomAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(SecurityUtils.getCurrentUser());
    }
}
