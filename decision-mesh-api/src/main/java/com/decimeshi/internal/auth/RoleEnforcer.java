package com.decimeshi.internal.auth;

import io.quarkus.security.ForbiddenException;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RoleEnforcer {

    public void require(Role required, Role actual) {
        if (actual.ordinal() > required.ordinal()) {
            throw new ForbiddenException("Insufficient privileges");
        }
    }
}
