create table mission_action
(
    id uuid not null
        constraint mission_action_pkey
            primary key,
    created_at timestamp not null,
    updated_at timestamp not null,
    answer varchar(255),
    mission_type varchar(25)      not null,
    creator_auth_key varchar(255)
        constraint fk_chat_creator_auth_key
        references tb_user (auth_key),
    mission_id uuid not null
);
