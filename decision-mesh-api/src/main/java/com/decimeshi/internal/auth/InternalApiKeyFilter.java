package com.decimeshi.internal.auth;

import io.quarkus.runtime.LaunchMode;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;
import java.util.Optional;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class InternalApiKeyFilter implements ContainerRequestFilter {

    @ConfigProperty(name = "internal.api.key")
    Optional<String> expectedKey;

    @Inject
    LaunchMode launchMode;

    @Override
    public void filter(ContainerRequestContext ctx) throws IOException {
        // INTERIM FIX: Skip authentication in DEV mode
        if (launchMode == LaunchMode.DEVELOPMENT) {
            return; // No auth in dev!
        }

        // Production: require API key
        String key = ctx.getHeaderString("X-Internal-API-Key");

        if (expectedKey.isEmpty() || key == null || !key.equals(expectedKey.get())) {
            ctx.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\":\"Unauthorized - API key required\"}")
                    .build());
        }
    }
}