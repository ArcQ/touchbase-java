create table mission_template
(
    id           uuid             not null
        constraint mission_template_pkey
            primary key,
    name         varchar(100)     not null,
    description  varchar(255)     not null,
    score_reward double precision not null,
    mission_type varchar(25)      not null,
    detail json NOT NULL
);

create table mission
(
    id                    uuid             not null
        constraint mission_pkey
            primary key,
    started_at            timestamp,
    created_at            timestamp,
    expires_at            timestamp,
    mission_template_id            uuid,
    user_auth_key varchar(255)      not null
        constraint fk_mission_assigned_user
            references tb_user (auth_key),
    base uuid             not null
        constraint fk_mission_base_base
            references base
);

CREATE INDEX mission_user_auth_key_base_idx ON mission (base, user_auth_key);
CREATE INDEX mission_idx ON mission (base);
