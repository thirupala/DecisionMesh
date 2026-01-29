package com.decimeshi.internal.decisions;

import com.decimeshi.api.resource.DecisionRequest;
import com.decimeshi.api.resource.DecisionResponse;
import com.decimeshi.internal.decisions.model.*;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class DecisionService {
    private static final Logger LOG = Logger.getLogger(DecisionService.class);

    @Inject
    DecisionRepository repo;

    // ---------------------------------------------------------
    // Single
    // ---------------------------------------------------------

    public DecisionEntity getDecision(UUID id) {
        return repo.findByDecisionId(id)
                .orElseThrow(() -> new NotFoundException("Decision not found"));
    }

    // ---------------------------------------------------------
    // Bulk
    // ---------------------------------------------------------

    public List<DecisionEntity> getBulk(BulkDecisionRequest request) {
        return repo.loadBulk(request.decisionIds());
    }

    // ---------------------------------------------------------
    // Search
    // ---------------------------------------------------------

    public List<DecisionEntity> search(DecisionSearchRequest req) {
        return repo.search(req.limit(), req.offset());
    }

    // ---------------------------------------------------------
    // Purge
    // ---------------------------------------------------------

    public PurgeResult purge(PurgeRequest req) {
        long count = repo.purgeOlderThan(
                req.olderThanDays(),
                req.tenantId()
        );
        return new PurgeResult((int) count, false);
    }

    public DecisionResponse processDecision(DecisionRequest request) {
        LOG.infof("Processing decision: %s", request.getDecisionId());

        // TODO: Implement actual business logic
        // 1. Validate request
        // 2. Evaluate policies
        // 3. Select provider
        // 4. Call AI provider
        // 5. Store result

        return new DecisionResponse(
                request.getDecisionId(),
                "accepted",
                "Decision processing initiated"
        );
    }
}
