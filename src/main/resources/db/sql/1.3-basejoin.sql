create table base_join
(
    id uuid not null
        constraint base_join_pkey
            primary key,
    created_at timestamp,
    updated_at timestamp,
    base_id uuid,
    base_join_action varchar(255),
    joining_user_auth_key varchar(255)
        constraint fk_base_join_tb_user_joining_user_auth_key
            references tb_user (auth_key),
    creator_auth_key varchar(255)
        constraint fk_base_join_tb_user_creator_auth_key
            references tb_user (auth_key)
);

CREATE INDEX base_join_idx ON base_join (base_id, joining_user_auth_key);
