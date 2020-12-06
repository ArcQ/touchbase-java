create table question
(
    id uuid not null
        constraint question_key
            primary key,
    created_at timestamp not null,
    updated_at timestamp not null,
    creator_auth_key varchar(255)
        constraint fk_chat_creator_auth_key
            references tb_user (auth_key),

    difficulty int4,
    question_type varchar(25) not null,
    choices text []
);
