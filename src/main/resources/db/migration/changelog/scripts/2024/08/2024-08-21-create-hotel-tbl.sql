--liquibase formatted sql

--changeset Mark Valakanouski:2024-08-21-create-hotel-tbl runOnChange:true

create table hotel
(
    id              bigserial
        primary key
        unique,
    name            varchar(50) not null,
    number_of_stars int         not null,
    country_id      bigint      not null
        constraint hotel_country_id_fk
            references country,
    city_id         bigint      not null
        constraint hotel_city_id_fk
            references city,
    number_of_rooms int         not null
);

--changeset Mark Valakanouski:2024-08-23-create-hotel-tbl runOnChange:true

alter table hotel
    drop constraint hotel_city_id_fk;

alter table hotel
    drop column city_id;


