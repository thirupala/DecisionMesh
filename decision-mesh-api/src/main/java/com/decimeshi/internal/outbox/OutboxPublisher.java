package com.decimeshi.internal.outbox;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class OutboxPublisher {

    @Inject
    @Channel("outbox-events")
    Emitter<String> emitter;

    @Transactional
    public void publishBatch() {

        for (Object obj : OutboxEvent.find("published", false)
                .page(0, 50)
                .list()) {

            publishSingle((OutboxEvent) obj);
        }
    }


    private void publishSingle(OutboxEvent event) {

        // Send payload directly
        emitter.send(event.payload);

        event.published = true;
        event.persist();
    }
}
