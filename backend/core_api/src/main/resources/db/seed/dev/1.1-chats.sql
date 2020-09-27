INSERT INTO chat (id,
                  base_id,
                  chat_id)
VALUES ('9ab75f52-e9d0-4c82-abbd-ceeae0d398e7',
        (SELECT id from base where name='base1'),
        'cf4aeae1-cda7-41f3-adf7-9b2bb377be7d'),
       ('94da60c2-4bc6-467f-9f69-278c8d3f0b62',
        (SELECT id from base where name='base2'),
        '83cdd361-54a2-4e5a-a6db-35e20fc54555'),
       ('c8718cdb-cf4d-4796-8693-aaf2d3341e9d',
        (SELECT id from base where name='base3'),
        '7a6ad1d6-551c-453a-9a66-879c2587ca0d');
