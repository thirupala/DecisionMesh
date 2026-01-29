package com.decimeshi.internal.decisions.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "decisions")
public class DecisionEntity extends PanacheEntityBase {

    // =========================================================
    // Primary key
    // =========================================================

    @Id
    @Column(name = "decision_id", nullable = false)
    public UUID decisionId;

    // =========================================================
    // Core fields
    // =========================================================

    @Column(name = "tenant_id", nullable = false)
    public String tenantId;

    @Column(name = "status", nullable = false)
    public String status;

    @Column(name = "decision_type", nullable = false)
    public String decisionType;

    // =========================================================
    // JSONB fields
    // =========================================================

    @Column(name = "intent", columnDefinition = "jsonb", nullable = false)
    public String intent;

    @Column(name = "constraints", columnDefinition = "jsonb", nullable = false)
    public String constraints;

    @Column(name = "context", columnDefinition = "jsonb", nullable = false)
    public String context;

    @Column(name = "outcome", columnDefinition = "jsonb")
    public String outcome;

    @Column(name = "execution_summary", columnDefinition = "jsonb")
    public String executionSummary;

    // =========================================================
    // Aggregates
    // =========================================================

    @Column(name = "total_cost_usd", precision = 12, scale = 6)
    public BigDecimal totalCostUsd;

    @Column(name = "total_latency_ms")
    public Integer totalLatencyMs;

    // =========================================================
    // Timestamps
    // =========================================================

    @Column(name = "created_at", nullable = false)
    public OffsetDateTime createdAt;

    @Column(name = "completed_at")
    public OffsetDateTime completedAt;

    @Column(name = "ttl_expires_at", nullable = false)
    public OffsetDateTime ttlExpiresAt;
}

