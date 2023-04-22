--liquibase formatted sql

--changeset sokolovRA:create_table_comment

create table comment
(
    id           SERIAL PRIMARY KEY,
    created_at   TIMESTAMP NOT NULL,
    text         varchar(255),
    author_id    integer references users (id),
    ads_id       integer references ads (id)
);