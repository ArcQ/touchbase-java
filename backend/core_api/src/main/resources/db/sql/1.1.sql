create table chat
(
    created_at timestamp,
    updated_at timestamp,
    base_uuid uuid not null
        constraint fk_base_chat_base_base_uuid
            references base,
    chat_id varchar(100) not null,
    constraint chat_pkey
        primary key (base_uuid, chat_id)
);
