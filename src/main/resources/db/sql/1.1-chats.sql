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
    creator_id uuid
        constraint fk_chat_creator_id
            references tb_user
);
