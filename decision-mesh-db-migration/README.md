# DecisionMesh Database Migration

Database schema migration module using Flyway.

## Usage

### Option 1: Run with Quarkus
```bash
# Run migrations
mvn compile quarkus:dev

# Show migration info
mvn compile quarkus:dev -Dquarkus.args=info

# Validate migrations
mvn compile quarkus:dev -Dquarkus.args=validate

# Clean database (dev only!)
mvn compile quarkus:dev -Dquarkus.args=clean
```

### Option 2: Run with Flyway Maven Plugin
```bash
# Migrate
mvn flyway:migrate -Plocal

# Show info
mvn flyway:info -Plocal

# Validate
mvn flyway:validate -Plocal

# Baseline existing database
mvn flyway:baseline -Plocal

# Repair
mvn flyway:repair -Plocal
```

### Option 3: Use Scripts
```bash
# Windows
migrate.bat          # Run migrations
migrate.bat info     # Show info
migrate.bat validate # Validate

# Or use Flyway directly
flyway-migrate.bat
flyway-info.bat
```

## Creating New Migrations

1. Create new SQL file: `src/main/resources/db/migration/V1.0.4__Description.sql`
2. Version format: `V{major}.{minor}.{patch}__{Description}.sql`
3. Run migration: `mvn compile quarkus:dev`

## Profiles

- `local` (default): localhost:5432
- `docker`: dm-postgres:5432
- `prod`: Uses environment variables

## Environment Variables
```bash
DB_URL=jdbc:postgresql://host:5432/database
DB_USERNAME=postgres
DB_PASSWORD=secret
```