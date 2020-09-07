INSERT INTO chat (base_uuid,
                  chat_id)
VALUES ((SELECT uuid from base where name='base1'),
        'cf4aeae1-cda7-41f3-adf7-9b2bb377be7d');

INSERT INTO chat (base_uuid,
                  chat_id)
VALUES ((SELECT uuid from base where name='base2'),
        '83cdd361-54a2-4e5a-a6db-35e20fc54555');

INSERT INTO chat (base_uuid,
                  chat_id)
VALUES ((SELECT uuid from base where name='base3'),
        '7a6ad1d6-551c-453a-9a66-879c2587ca0d');
