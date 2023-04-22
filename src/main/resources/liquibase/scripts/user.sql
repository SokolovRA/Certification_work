

--liquibase formatted sql

--changeset sokolovRA:create_table_users

create table users
(
    id                      SERIAL PRIMARY KEY ,
    first_name              varchar(255),
    last_name               varchar(255),
    username                varchar(255),
    password                varchar(255),
    enabled                 BOOLEAN,
    phone                   varchar(255),
    image_id                integer references image (id),
    role                    varchar(255)
);