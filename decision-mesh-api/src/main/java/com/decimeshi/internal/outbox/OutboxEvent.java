package com.decimeshi.internal.outbox;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "outbox_events")
public class OutboxEvent extends PanacheEntityBase {

    @Id
    @Column(name = "event_id")
    public UUID eventId;

    @Column(name = "aggregate_id", nullable = false)
    public UUID aggregateId;

    @Column(name = "event_type", nullable = false)
    public String eventType;

    @Column(name = "payload", columnDefinition = "jsonb", nullable = false)
    public String payload;

    @Column(name = "published", nullable = false)
    public boolean published;

    @Column(name = "created_at", nullable = false)
    public OffsetDateTime createdAt;
}


