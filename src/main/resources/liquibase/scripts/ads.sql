--liquibase formatted sql

--changeset sokolovRA:create_table_ads

create table ads
(
    id          SERIAL PRIMARY KEY,
    title       varchar(255),
    description varchar(255),
    price       integer,
    author_id   integer references users (id),
    image_id    integer references image (id)
);