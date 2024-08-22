--liquibase formatted sql

--changeset Mark Valakanouski:2024-08-21-create-document-tbl runOnChange:true

create table document
(
    id         bigserial
        primary key
        unique,
    file_name  varchar(50) not null,
    file_data  bytea,
    created_at timestamp(6)
);

comment on table document is 'Таблица для хранения документов';
comment on column document.id is 'Уникальный идентификатор записи';
comment on column document.file_name is 'Название файла';
comment on column document.file_data is 'Содержимое файла';
comment on column document.created_at is 'Дата и время создания';

