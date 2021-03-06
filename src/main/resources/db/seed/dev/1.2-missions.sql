INSERT INTO mission_template (id,
                              name,
                              description,
                              score_reward,
                              mission_type,
                              detail)
VALUES ('8f59d919-3830-4e1b-8a8c-be2b2b36a389',
        'easy mission',
        'this is a easy mission',
        10.0,
        'PERIODIC', '{
          "questions": [{
            "id": "56603a65-cdc4-4137-9cad-48ff40930eea",
            "text": "What tv shows have you been watching lately?"
          }]
        }'),
       ('d657894d-c560-48c3-8d7a-bec719197374',
        'medium mission',
        'this is a medium difficulty mission',
        10.0,
        'PERIODIC',
        '{
          "questions": [{
            "id": "56603a65-cdc4-4137-9cad-48ff40930eea",
            "text": "What tv shows have you been watching lately?"
          }]
        }');

INSERT INTO mission (id,
                     started_at,
                     created_at,
                     expires_at,
                     user_auth_key,
                     base,
                     mission_template_id
)
VALUES ('287a7518-0547-45d8-a79e-9c920effbb31',
        '08-20-2020',
        '08-21-2020',
        '08-21-2020',
        (SELECT tb_user.auth_key from tb_user where tb_user.username = 'arcq'),
        (SELECT id from base where base.name = 'base1'),
        '8f59d919-3830-4e1b-8a8c-be2b2b36a389'),
       ('52c6db7b-928d-462d-9a4d-683dcca8bdcb',
        '08-20-2020',
        '08-21-2020',
        '08-21-2021',
        (SELECT tb_user.auth_key from tb_user where tb_user.username = 'sita'),
        (SELECT id from base where base.name = 'base2'),
        'd657894d-c560-48c3-8d7a-bec719197374');
