package fr.paymybuddy.model.SecurityTest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

class TestSecurityContext implements SecurityContext {
    @Override
    public Authentication getAuthentication() {
        return null;
    }

    @Override
    public void setAuthentication(Authentication authentication) {

    }
}
