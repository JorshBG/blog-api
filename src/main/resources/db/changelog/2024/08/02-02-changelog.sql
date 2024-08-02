-- liquibase formatted sql

-- changeset jordi:1722580013637-1
ALTER TABLE comments
    ALTER COLUMN author SET NOT NULL;

-- changeset jordi:1722580013637-2
ALTER TABLE comments
    ALTER COLUMN content SET NOT NULL;

-- changeset jordi:1722580013637-3
ALTER TABLE comments
    ALTER COLUMN post_id SET NOT NULL;

