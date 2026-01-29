package com.decimeshi.debug;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import org.eclipse.microprofile.config.ConfigProvider;

@ApplicationScoped
public class ConfigCheck {

    void onStart(@Observes StartupEvent ev) {
        var config = ConfigProvider.getConfig();

        System.out.println("\n========================================");
        System.out.println("CONFIGURATION CHECK:");
        System.out.println("========================================");

        // Check Flyway config
        var flywayMigrate = config.getOptionalValue("quarkus.flyway.migrate-at-start", Boolean.class);
        System.out.println("quarkus.flyway.migrate-at-start = " + flywayMigrate.orElse(null));

        var flywayClean = config.getOptionalValue("quarkus.flyway.clean-at-start", Boolean.class);
        System.out.println("quarkus.flyway.clean-at-start = " + flywayClean.orElse(null));

        var flywayLocations = config.getOptionalValue("quarkus.flyway.locations", String.class);
        System.out.println("quarkus.flyway.locations = " + flywayLocations.orElse("NOT SET"));

        // Check Scheduler config
        var schedulerEnabled = config.getOptionalValue("quarkus.scheduler.enabled", Boolean.class);
        System.out.println("quarkus.scheduler.enabled = " + schedulerEnabled.orElse(null));

        // Check Hibernate config
        var hibernateGen = config.getOptionalValue("quarkus.hibernate-orm.database.default-schema-management.generation", String.class);
        System.out.println("quarkus.hibernate-orm.database.default-schema-management.generation = " + hibernateGen.orElse("NOT SET"));

        System.out.println("========================================\n");
    }
}