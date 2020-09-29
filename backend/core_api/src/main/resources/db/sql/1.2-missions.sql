create table mission_template
(
    id uuid not null
        constraint mission_template_pkey
            primary key,
    created_at timestamp,
    updated_at timestamp,
    name varchar(100) not null,
    description varchar(255) not null,
    score_reward double precision not null,
    mission_type varchar(25) not null,
    creator_id uuid
        constraint fk_chat_creator_id
            references tb_user
);

create table mission
(
    id uuid not null
        constraint mission_pkey
            primary key,
    name varchar(100) not null,
    description varchar(255) not null,
    score_reward double precision not null,
    mission_type varchar(25) not null,
    started_at timestamp,
    created_at timestamp,
    mission_assigned_user uuid not null
        constraint fk_mission_assigned_user
            references tb_user,
    mission_assigned_base uuid not null
        constraint fk_mission_base_base
            references base
);


