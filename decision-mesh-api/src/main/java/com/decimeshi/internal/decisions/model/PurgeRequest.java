package com.decimeshi.internal.decisions.model;

public record PurgeRequest(
        int olderThanDays,
        String tenantId,
        boolean dryRun,
        boolean confirm
) {

    public PurgeRequest {
        if (olderThanDays < 30) {
            throw new IllegalArgumentException("olderThanDays must be >= 30");
        }
        if (!confirm) {
            throw new IllegalArgumentException("confirm=true is required for purge");
        }
    }
}
