create table account
(
    id           uuid                        not null default uuid_generate_v4()
        constraint pk_account__id primary key,
    created_at   timestamp without time zone not null default now_utc(),
    updated_at   timestamp without time zone not null default now_utc(),
    lock_version bigint                      not null default 0
        constraint cc_account__lock_version_positive check (lock_version > -1),

    name         varchar(32)                 not null
        constraint uc_account_name unique,
    token        varchar(255)                not null
);
