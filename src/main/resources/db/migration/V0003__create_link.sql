create table link
(
    id           uuid                        not null default uuid_generate_v4()
        constraint pk_link__id primary key,
    created_at   timestamp without time zone not null default now_utc(),
    last_used_at timestamp without time zone not null default now_utc(),

    code         varchar(16)                 not null
        constraint uc_link__code unique,
    url          varchar(2048)               not null

);
