--liquibase formatted sql

--changeset sokolovRA:create_table_ads

create table ads
(
    id          SERIAL PRIMARY KEY,
    title       varchar(255) NOT NULL,
    description varchar(255),
    price       integer NOT NULL,
    author_id   integer references users (id),
    image_id    integer references image (id)
);