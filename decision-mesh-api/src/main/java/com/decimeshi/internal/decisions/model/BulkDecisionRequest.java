package com.decimeshi.internal.decisions.model;

import java.util.List;
import java.util.UUID;

public record BulkDecisionRequest(
        List<UUID> decisionIds
) {

    public BulkDecisionRequest {
        if (decisionIds == null || decisionIds.isEmpty()) {
            throw new IllegalArgumentException("decisionIds must not be empty");
        }
        if (decisionIds.size() > 100) {
            throw new IllegalArgumentException("Maximum 100 decisionIds allowed");
        }
    }
}
