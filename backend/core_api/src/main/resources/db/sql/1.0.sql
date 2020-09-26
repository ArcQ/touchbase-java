create table mission
(
    id uuid not null
        constraint mission_pkey
            primary key,
    created_at timestamp,
    updated_at timestamp,
    description varchar(255),
    mission_type integer,
    name varchar(255),
    score_reward double precision
);

create table tb_user
(
    id uuid not null
        constraint tb_user_pkey
            primary key,
    created_at timestamp,
    updated_at timestamp,
    auth_key varchar(255),
    email varchar(255),
    first_name varchar(255),
    image_url varchar(255),
    last_name varchar(255),
    score double precision,
    username varchar(255)
);

create table base
(
    id uuid not null
        constraint base_pkey
            primary key,
    created_at timestamp,
    updated_at timestamp,
    image_url varchar(255),
    is_active boolean not null,
    name varchar(255),
    score double precision,
    creator_id uuid
        constraint fk_base_user_creator_id
            references tb_user
);

create table base_member
(
    id uuid not null
        constraint base_member_pkey
            primary key,
    created_at timestamp,
    updated_at timestamp,
    role varchar(255) not null,
    base_id uuid not null
        constraint fk_base_member_base_base_id
            references base,
    member_id uuid not null
        constraint fk_base_member_tb_user_member_id
            references tb_user
);

create table base_join
(
    id uuid not null
        constraint base_join_pkey
            primary key,
    created_at timestamp,
    updated_at timestamp,
    base_id varchar(255),
    base_join_action integer,
    creator_id uuid
        constraint fk_base_join_tb_user_creator_id
            references tb_user
);

