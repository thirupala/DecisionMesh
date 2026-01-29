package com.decimeshi.api.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

public class DecisionRequest {

    @NotBlank(message = "decision_id is required")
    @JsonProperty("decision_id")
    private String decisionId;

    @NotBlank(message = "decision_type is required")
    @JsonProperty("decision_type")
    private String decisionType;

    @NotNull(message = "intent is required")
    private Map<String, Object> intent;

    private Map<String, Object> constraints;

    @NotNull(message = "context is required")
    private Map<String, Object> context;

    // Constructors
    public DecisionRequest() {
    }

    public DecisionRequest(String decisionId, String decisionType,
                           Map<String, Object> intent,
                           Map<String, Object> constraints,
                           Map<String, Object> context) {
        this.decisionId = decisionId;
        this.decisionType = decisionType;
        this.intent = intent;
        this.constraints = constraints;
        this.context = context;
    }

    // Getters and Setters
    public String getDecisionId() {
        return decisionId;
    }

    public void setDecisionId(String decisionId) {
        this.decisionId = decisionId;
    }

    public String getDecisionType() {
        return decisionType;
    }

    public void setDecisionType(String decisionType) {
        this.decisionType = decisionType;
    }

    public Map<String, Object> getIntent() {
        return intent;
    }

    public void setIntent(Map<String, Object> intent) {
        this.intent = intent;
    }

    public Map<String, Object> getConstraints() {
        return constraints;
    }

    public void setConstraints(Map<String, Object> constraints) {
        this.constraints = constraints;
    }

    public Map<String, Object> getContext() {
        return context;
    }

    public void setContext(Map<String, Object> context) {
        this.context = context;
    }

    // Helper method
    public String getTenantId() {
        return context != null ? (String) context.get("tenant_id") : "default";
    }
}