package com.decimeshi.internal.decisions.model;

import java.time.OffsetDateTime;

public record DecisionSearchRequest(
        Filters filters,
        String sortBy,
        String sortOrder,
        int limit,
        int offset
) {

    // Defaults matching OpenAPI
    public DecisionSearchRequest {
        if (sortBy == null) {
            sortBy = "created_at";
        }
        if (sortOrder == null) {
            sortOrder = "desc";
        }
        if (limit == 0) {
            limit = 20;
        }
    }

    // --------------------------------------------------
    // Validation (called explicitly by repository/service)
    // --------------------------------------------------

    public void validate() {
        if (limit < 1 || limit > 100) {
            throw new IllegalArgumentException("limit must be between 1 and 100");
        }
        if (offset < 0) {
            throw new IllegalArgumentException("offset must be >= 0");
        }
        if (filters != null && filters.dateRange() != null) {
            var dr = filters.dateRange();
            if (dr.start() != null && dr.end() != null &&
                    dr.start().isAfter(dr.end())) {
                throw new IllegalArgumentException("dateRange.start must be before dateRange.end");
            }
        }
    }

    // ==================================================
    // Nested types
    // ==================================================

    public record Filters(
            String tenantId,
            String decisionType,
            Status status,
            DateRange dateRange,
            CostRange costRange
    ) {}

    public enum Status {
        SUCCESS,
        FAILURE
    }

    public record DateRange(
            OffsetDateTime start,
            OffsetDateTime end
    ) {}

    public record CostRange(
            Double min,
            Double max
    ) {}
}
