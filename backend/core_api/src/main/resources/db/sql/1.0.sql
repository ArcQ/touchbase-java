create table if not exists mission
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

create table if not exists person
(
    uuid uuid not null
        constraint person_pkey
            primary key,
    created_at timestamp,
    updated_at timestamp,
    auth_id varchar(255),
    email varchar(255),
    first_name varchar(255),
    last_name varchar(255),
    score double precision,
    username varchar(255),
    creator_uuid uuid
        constraint fkiyq15ay1ijamkhau9pihc77vs
            references person
);

create table if not exists base
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
        constraint fk4ixaqaak8kkd1oi5rrloqdbgo
            references person
);

create table if not exists base_person
(
    base_uuid uuid not null
        constraint fkhqedgbrs5j5kmyxjvwfmbmajy
            references base,
    members_uuid uuid not null
        constraint fkpy7gw1fpbkt0se4iarp4muavy
            references person,
    owners_uuid uuid not null
        constraint fk9t86bupqsyqlqo3vyceipid1l
            references person,
    constraint base_person_pkey
        primary key (base_uuid, owners_uuid)
);

create table if not exists base_join
(
    uuid uuid not null
        constraint base_join_pkey
            primary key,
    created_at timestamp,
    updated_at timestamp,
    base_id varchar(255),
    base_join_action integer,
    creator_uuid uuid
        constraint fk2hgopac4s2famicfu0peejrpa
            references person
);

create table if not exists base_join_person
(
    base_join_uuid uuid not null
        constraint fki5l5558cwv1nbo6300fpjb9uk
            references base_join,
    owners_uuid uuid not null
        constraint fk5sjj1dtitaq83yhyexdrc6or0
            references person,
    constraint base_join_person_pkey
        primary key (base_join_uuid, owners_uuid)
);

