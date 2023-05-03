--liquibase formatted sql

--changeset sokolovRA:create_table_image

create table image
(
    id         SERIAL PRIMARY KEY,
    file_size  bigint,
    media_type varchar(255),
    data bytea
);