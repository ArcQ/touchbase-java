create table news_article
(
    id uuid not null
        constraint newsarticle_pkey
            primary key,
    created_at timestamp,
    updated_at timestamp,
    expires_at timestamp,
    topic varchar(255),
    data jsonb,
    creator_auth_key varchar(255)
        constraint fk_base_join_tb_user_creator_auth_key
            references tb_user (auth_key)
);

CREATE INDEX news_article_query_idx ON news_article (topic);
