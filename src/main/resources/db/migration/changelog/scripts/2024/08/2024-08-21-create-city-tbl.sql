--liquibase formatted sql

--changeset Mark Valakanouski:2024-08-21-create-city-tbl runOnChange:true

create table city
(
    id         bigserial
        primary key,
    name       varchar(50) not null
        unique,
    country_id bigint      not null
        constraint city_city_id_fk
            references public.country
);

