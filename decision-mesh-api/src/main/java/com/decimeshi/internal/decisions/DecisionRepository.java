package com.decimeshi.internal.decisions;

import com.decimeshi.internal.decisions.model.*;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.OffsetDateTime;
import java.util.*;

@ApplicationScoped
public class DecisionRepository
        implements PanacheRepositoryBase<DecisionEntity, UUID> {

    // ---------------------------------------------------------
    // Single
    // ---------------------------------------------------------

    public Optional<DecisionEntity> findByDecisionId(UUID id) {
        return find("decisionId", id).firstResultOptional();
    }

    // ---------------------------------------------------------
    // Bulk
    // ---------------------------------------------------------

    public List<DecisionEntity> loadBulk(List<UUID> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }
        return find("decisionId IN ?1", ids).list();
    }

    // ---------------------------------------------------------
    // Search (simple version, extend later)
    // ---------------------------------------------------------

    public List<DecisionEntity> search(int limit, int offset) {
        return findAll()
                .page(offset / limit, limit)
                .list();
    }

    // ---------------------------------------------------------
    // Purge
    // ---------------------------------------------------------

    @Transactional
    public long purgeOlderThan(int olderThanDays, String tenantId) {

        OffsetDateTime cutoff = OffsetDateTime.now().minusDays(olderThanDays);

        if (tenantId == null) {
            return delete("ttlExpiresAt < ?1", cutoff);
        }

        return delete(
                "ttlExpiresAt < ?1 AND tenantId = ?2",
                cutoff,
                tenantId
        );
    }
}
