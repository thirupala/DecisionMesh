package com.decimeshi.internal.outbox;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class OutboxScheduler {

    @Inject
    OutboxPublisher publisher;

    @Scheduled(every = "5s")
    void publish() {
        publisher.publishBatch();
    }
}
