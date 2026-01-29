package com.decimeshi.internal.decisions.model;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DecisionTrace {

    public UUID decisionId;
    public List<PolicyEvaluation> policyEvaluations;
    public List<ExecutionAttempt> executionAttempts;
    public RoutingDecision routingDecision;

    public static class PolicyEvaluation {
        public String policyId;
        public int policyVersion;
        public OffsetDateTime evaluatedAt;
        public Outcome outcome;
        public Map<String, Object> modifications;
        public int evaluationTimeMs;

        public enum Outcome {
            ALLOW,
            DENY,
            MODIFY
        }
    }

    public static class ExecutionAttempt {
        public int attemptNumber;
        public String provider;
        public String model;
        public OffsetDateTime startedAt;
        public OffsetDateTime completedAt;
        public Status status;
        public Map<String, Object> error;
        public int latencyMs;
        public double costUsd;
        public Map<String, Object> tokens;

        public enum Status {
            SUCCESS,
            FAILURE,
            TIMEOUT
        }
    }

    public static class RoutingDecision {
        public Strategy strategy;
        public String rationale;
        public List<String> alternativePathsConsidered;

        public enum Strategy {
            COST_OPTIMIZED,
            LATENCY_OPTIMIZED,
            QUALITY_OPTIMIZED,
            POLICY_REQUIRED
        }
    }
}
