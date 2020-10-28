create table chat
(
    id         uuid         not null
        constraint chat_pkey
            primary key,
    created_at timestamp,
    updated_at timestamp,
    base_id    uuid         not null
        constraint fk_base_chat_base_base_uuid
            references base,
    chat_id    varchar(100) not null,
    creator_auth_key varchar(255)
        constraint fk_chat_creator_auth_key
            references tb_user (auth_key)
);
