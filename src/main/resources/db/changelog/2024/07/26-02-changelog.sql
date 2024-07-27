-- liquibase formatted sql

-- changeset jordi:1722044816748-3
ALTER TABLE comments DROP CONSTRAINT fk_comments_on_post;

-- changeset jordi:1722044816748-1
ALTER TABLE comments
    ADD post_id BIGINT;

-- changeset jordi:1722044816748-2
ALTER TABLE comments
    ADD CONSTRAINT FK_COMMENTS_ON_POST FOREIGN KEY (post_id) REFERENCES posts (id);

-- changeset jordi:1722044816748-4
ALTER TABLE comments DROP COLUMN post;

