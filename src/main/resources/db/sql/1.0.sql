create table tb_user
(
    id         uuid not null
        constraint tb_user_pkey
            primary key,
    created_at timestamp,
    updated_at timestamp,
    auth_key   varchar(255) NOT NULL UNIQUE,
    email      varchar(255),
    first_name varchar(255),
    image_url  varchar(255),
    last_name  varchar(255),
    score      double precision,
    username   varchar(255),
    creator_auth_key varchar(255)
        constraint fk_base_user_creator_auth_key
            references tb_user (auth_key)
);

create table base
(
    id         uuid    not null
        constraint base_pkey
            primary key,
    created_at timestamp,
    updated_at timestamp,
    image_url  varchar(255),
    is_active  boolean not null,
    name       varchar(255),
    score      double precision,
    creator_auth_key varchar(255)
        constraint fk_base_user_creator_auth_key
            references tb_user (auth_key)
);

create table base_member
(
    id         uuid         not null
        constraint base_member_pkey
            primary key,
    created_at timestamp,
    updated_at timestamp,
    role       varchar(255) not null,
    creator_auth_key       varchar(255)
        constraint fk_base_member_tb_user_creator_auth_key
            references tb_user (auth_key),
    base_id    uuid         not null
        constraint fk_base_member_base_base_id
            references base,
    member_auth_key  varchar(255)         not null
        constraint fk_base_member_tb_user_member_auth_key
            references tb_user (auth_key)
);
