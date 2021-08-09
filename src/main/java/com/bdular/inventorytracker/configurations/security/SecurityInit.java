package com.bdular.inventorytracker.configurations.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.stereotype.Component;

@Component
public class SecurityInit extends AbstractSecurityWebApplicationInitializer {
    public SecurityInit() {
        super(SecurityConfiguration.class);
    }
}
