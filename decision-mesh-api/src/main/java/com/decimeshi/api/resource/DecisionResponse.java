package com.decimeshi.api.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

public class DecisionResponse {

    @JsonProperty("decision_id")
    private String decisionId;

    private String status;
    private String message;
    private Instant timestamp;

    // Constructors
    public DecisionResponse() {
        this.timestamp = Instant.now();
    }

    public DecisionResponse(String decisionId, String status, String message) {
        this.decisionId = decisionId;
        this.status = status;
        this.message = message;
        this.timestamp = Instant.now();
    }

    // Getters and Setters
    public String getDecisionId() {
        return decisionId;
    }

    public void setDecisionId(String decisionId) {
        this.decisionId = decisionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}