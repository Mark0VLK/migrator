--liquibase formatted sql

--changeset Mark Valakanouski:2024-08-21-create-country-tbl runOnChange:true

create table country
(
    id   bigserial
        primary key
        unique,
    name varchar(50) not null
        unique
);