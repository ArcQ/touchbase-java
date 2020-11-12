CREATE TABLE expo_push_token (
                                 id uuid not null
                                     constraint expo_push_token.
                                         primary key,
                                 user_auth_key varchar(255)
                                     constraint fk_expo_push_token_tb_user_creator_auth_key
                                         references tb_user (auth_key),
                                 token varchar(1024),
                                 device_id varchar(1024),
                                 is_valid bit default 1,
                                 expires_at timestamp,
                                 created_at timestamp,
                                 updated_at timestamp
);

CREATE INDEX expo_push_token_agent_id_idx ON expo_push_token (user_auth_key, is_valid);
CREATE INDEX expo_push_token_device_id_idx ON expo_push_token (token);
