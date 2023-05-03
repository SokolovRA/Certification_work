

--liquibase formatted sql

--changeset sokolovRA:create_table_users

create table users
(
    id                      SERIAL PRIMARY KEY ,
    first_name              varchar(255) NOT NULL,
    last_name               varchar(255) NOT NULL,
    username                varchar(255) NOT NULL,
    password                varchar(255) NOT NULL,
    enabled                 BOOLEAN,
    phone                   varchar(255) NOT NULL,
    image_id                integer references image (id),
    role                    varchar(255)
);