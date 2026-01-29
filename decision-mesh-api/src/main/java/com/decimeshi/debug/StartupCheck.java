package com.decimeshi.debug;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import java.net.URL;

@ApplicationScoped
public class StartupCheck {

    void onStart(@Observes StartupEvent ev) {
        System.out.println("\n========================================");
        System.out.println("STARTUP DIAGNOSTICS:");
        System.out.println("========================================");

        // Check if Flyway is on classpath
        try {
            Class.forName("org.flywaydb.core.Flyway");
            System.out.println("✅ Flyway classes found on classpath");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Flyway NOT on classpath - dependency missing!");
        }

        // Check if migration file exists
        URL migration = getClass().getClassLoader()
                .getResource("db/migration/V1__initial_schema.sql");

        if (migration != null) {
            System.out.println("✅ Migration file found: " + migration);
        } else {
            System.out.println("❌ Migration file NOT found!");
        }

        System.out.println("========================================\n");
    }
}
