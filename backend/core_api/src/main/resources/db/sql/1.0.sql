create table mission
(
    uuid uuid not null
        constraint mission_pkey
            primary key,
    created_at timestamp,
    updated_at timestamp,
    description varchar(255),
    misson_type integer,
    name varchar(255),
    score_reward double precision
);

create table tb_user
(
    uuid uuid not null
        constraint tb_user_pkey
            primary key,
    created_at timestamp,
    updated_at timestamp,
    auth_id varchar(255),
    email varchar(255),
    first_name varchar(255),
    image_url varchar(255),
    last_name varchar(255),
    score double precision,
    username varchar(255)
);

create table base
(
    uuid uuid not null
        constraint base_pkey
            primary key,
    created_at timestamp,
    updated_at timestamp,
    image_url varchar(255),
    is_active boolean not null,
    name varchar(255),
    score double precision,
    creator_uuid uuid
        constraint fk22ncmdygalwwj4ttqtvugup0n
            references tb_user
);

create table base_member
(
    base_uuid uuid not null
        constraint fkt6j9uk6wrten8qcnfegeyi3jk
            references base,
    members_uuid uuid not null
        constraint fkovvwgvbsypfnxm3ss3mdk2x64
            references tb_user,
    constraint base_member_pkey
        primary key (base_uuid, members_uuid)
);

create table base_join
(
    uuid uuid not null
        constraint base_join_pkey
            primary key,
    created_at timestamp,
    updated_at timestamp,
    base_id varchar(255),
    base_join_action integer,
    creator_uuid uuid
        constraint fkhw7sx7xw92ys7ej169xofwc28
            references tb_user
);

create table entity_admin
(
    base_join_uuid uuid not null
        constraint fksix9t0x9jtu1i70num40o6oc2
            references base_join,
    admins_uuid uuid not null
        constraint fk7ervd96doyus2se86go2lpdv1
            references tb_user,
    base_uuid uuid not null
        constraint fk7psmg55hik37e2ysreoim998l
            references base,
    constraint entity_admin_pkey
        primary key (base_uuid, admins_uuid)
);

