-- liquibase formatted sql

-- changeset jordi:1724010483976-4
ALTER TABLE tags
    ADD CONSTRAINT uc_tags_name UNIQUE (name);

-- changeset jordi:1724010483976-1
ALTER TABLE tags
    ALTER COLUMN active SET DEFAULT TRUE;

-- changeset jordi:1724010483976-2
ALTER TABLE tags
    ALTER COLUMN name TYPE VARCHAR(60) USING (name::VARCHAR(60));

-- changeset jordi:1724010483976-3
ALTER TABLE tags
    ALTER COLUMN name SET NOT NULL;

