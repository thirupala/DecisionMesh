-- ============================================================
-- Extensions
-- ============================================================

CREATE
EXTENSION IF NOT EXISTS "uuid-ossp";

-- ============================================================
-- DECISIONS
-- ============================================================

CREATE TABLE decisions
(
    decision_id       UUID PRIMARY KEY     DEFAULT uuid_generate_v4(),
    tenant_id         TEXT        NOT NULL,
    status            TEXT        NOT NULL,
    decision_type     TEXT        NOT NULL,
    intent            JSONB       NOT NULL,
    constraints       JSONB       NOT NULL,
    context           JSONB       NOT NULL,
    outcome           JSONB,
    execution_summary JSONB,
    total_cost_usd    NUMERIC(12, 6),
    total_latency_ms  INTEGER,
    created_at        TIMESTAMPTZ NOT NULL DEFAULT now(),
    completed_at      TIMESTAMPTZ,
    ttl_expires_at    TIMESTAMPTZ NOT NULL,
    CONSTRAINT decisions_status_check
        CHECK (status IN ('pending', 'executing', 'success', 'failure', 'timeout'))
);

-- ============================================================
-- DECISION TRACES
-- ============================================================

CREATE TABLE decision_traces
(
    trace_id    BIGSERIAL PRIMARY KEY,
    decision_id UUID        NOT NULL
        REFERENCES decisions (decision_id) ON DELETE CASCADE,
    trace_type  VARCHAR(50) NOT NULL,
    payload     JSONB       NOT NULL,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT trace_type_check
        CHECK (trace_type IN (
                              'POLICY_EVALUATION',
                              'EXECUTION_ATTEMPT',
                              'ROUTING_DECISION'
            ))
);

-- ============================================================
-- POLICIES (VERSIONED)
-- ============================================================

CREATE TABLE policies
(
    policy_id          TEXT        NOT NULL,
    version            INTEGER     NOT NULL,
    name               TEXT,
    description        TEXT,
    priority           INTEGER,
    scope              JSONB       NOT NULL,
    conditions         JSONB       NOT NULL,
    actions            JSONB       NOT NULL,
    active             BOOLEAN     NOT NULL DEFAULT true,
    supersedes_version INTEGER,

    created_at         TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at         TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by         TEXT,
    PRIMARY KEY (policy_id, version)
);

-- ============================================================
-- PROVIDERS (CONFIG)
-- ============================================================

CREATE TABLE provider_configs
(
    provider_id            VARCHAR(50) PRIMARY KEY,
    name                   TEXT        NOT NULL,
    enabled                BOOLEAN     NOT NULL DEFAULT true,
    models                 JSONB       NOT NULL,
    rate_limits            JSONB,
    timeout_ms             INTEGER,
    circuit_breaker_config JSONB,
    created_at             TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at             TIMESTAMPTZ NOT NULL DEFAULT now()
);

-- ============================================================
-- PROVIDER RUNTIME STATE
-- ============================================================

CREATE TABLE provider_runtime_state
(
    provider_id     VARCHAR(50) PRIMARY KEY
        REFERENCES provider_configs (provider_id) ON DELETE CASCADE,
    status          VARCHAR(20) NOT NULL,
    circuit_state   VARCHAR(20) NOT NULL,

    failure_count   INTEGER     NOT NULL DEFAULT 0,
    last_failure_at TIMESTAMPTZ,
    next_retry_at   TIMESTAMPTZ,
    updated_at      TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT provider_status_check
        CHECK (status IN ('healthy', 'degraded', 'unavailable')),
    CONSTRAINT provider_circuit_state_check
        CHECK (circuit_state IN ('closed', 'open', 'half_open'))
);

-- ============================================================
-- OUTBOX
-- ============================================================

CREATE TABLE outbox_events
(
    event_id     UUID PRIMARY KEY      DEFAULT uuid_generate_v4(),
    aggregate_id UUID         NOT NULL,
    event_type   VARCHAR(100) NOT NULL,
    payload      JSONB        NOT NULL,
    created_at   TIMESTAMPTZ  NOT NULL DEFAULT now(),
    published    BOOLEAN      NOT NULL DEFAULT false
);

-- ============================================================
-- INDEXES
-- ============================================================

CREATE INDEX idx_decisions_tenant_created
    ON decisions (tenant_id, created_at DESC);

CREATE INDEX idx_decisions_type_created
    ON decisions (decision_type, created_at DESC);

CREATE INDEX idx_decisions_status
    ON decisions (status);

CREATE INDEX idx_decisions_cost
    ON decisions (total_cost_usd);

CREATE INDEX idx_decisions_created
    ON decisions (created_at DESC);

CREATE INDEX idx_decisions_ttl
    ON decisions (ttl_expires_at);

CREATE INDEX idx_decision_traces_decision_created
    ON decision_traces (decision_id, created_at);

CREATE INDEX idx_traces_provider
    ON decision_traces
    USING GIN ((payload -> 'provider'));

CREATE INDEX idx_traces_type
    ON decision_traces (trace_type);

CREATE INDEX idx_outbox_unpublished
    ON outbox_events (created_at) WHERE published = false;

CREATE UNIQUE INDEX idx_outbox_unique_event
    ON outbox_events (aggregate_id, event_type) WHERE published = false;

CREATE
MATERIALIZED VIEW mv_cost_by_provider AS
SELECT t.payload ->> 'provider' AS provider, COUNT (*) AS decision_count, SUM (d.total_cost_usd) AS total_cost
FROM decision_traces t
    JOIN decisions d
ON d.decision_id = t.decision_id
WHERE t.trace_type = 'EXECUTION_ATTEMPT'
GROUP BY provider;

