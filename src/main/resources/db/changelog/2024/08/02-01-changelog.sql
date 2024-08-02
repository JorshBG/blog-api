-- liquibase formatted sql

-- changeset jordi:1722579932635-1
ALTER TABLE posts
    ALTER COLUMN author SET NOT NULL;

