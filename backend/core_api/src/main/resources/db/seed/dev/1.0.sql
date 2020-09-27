INSERT INTO tb_user (id,
                     created_at,
                     updated_at,
                     auth_key,
                     email,
                     first_name,
                     last_name,
                     image_url,
                     score,
                     username)
VALUES ('40e6215d-b5c6-4896-987c-f30f3678f608',
        TO_DATE(cast('08-15-2020' as TEXT), 'MM-WW-YYYY'),
        TO_DATE(cast('08-15-2020' as TEXT), 'MM-WW-YYYY'),
        '129830df-f45a-46b3-b766-2101db28ea62',
        'eddielaw296@gmail.com',
        'eddie',
        'law',
        'https://images.unsplash.com/photo-1597476934600-ef660b4ce617?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1950&q=80',
        0,
        'arcq'),
       ('1544caf8-df80-11ea-87d0-0242ac130003',
        TO_DATE(cast('08-15-2020' as TEXT), 'MM-WW-YYYY'),
        TO_DATE(cast('08-15-2020' as TEXT), 'MM-WW-YYYY'),
        '5728dfb5-d089-48f1-aa9c-f1ea436fa8b1',
        'alanna.tai@gmail.com',
        'alanna',
        'tai',
        'https://images.unsplash.com/photo-1597476934600-ef660b4ce617?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1950&q=80',
        0,
        'sita'),
       ('56431cd1-6724-4ac9-af64-08c74d8df027',
        TO_DATE(cast('08-15-2020' as TEXT), 'MM-WW-YYYY'),
        TO_DATE(cast('08-15-2020' as TEXT), 'MM-WW-YYYY'),
        '5728dfb5-d089-48f1-aa9c-f1ea436fa8b1',
        'eltonlaw296@gmail.com',
        'elton',
        'law',
        'https://images.unsplash.com/photo-1597588405595-982407b950d2?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=701&q=80',
        0,
        'd0nkers');

INSERT INTO base (id,
                  created_at,
                  updated_at,
                  name,
                  score,
                  image_url,
                  is_active)
VALUES ('6034f8e2-df82-11ea-87d0-0242ac130003',
        TO_DATE(cast('08-15-2020' as TEXT), 'MM-WW-YYYY'),
        TO_DATE(cast('08-15-2020' as TEXT), 'MM-WW-YYYY'),
        'base1',
        0.0,
        'https://images.unsplash.com/photo-1597476934600-ef660b4ce617?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1950&q=80',
        true),
       ('a510a7cc-df82-11ea-87d0-0242ac130003',
        TO_DATE(cast('08-17-2020' as TEXT), 'MM-WW-YYYY'),
        TO_DATE(cast('08-17-2020' as TEXT), 'MM-WW-YYYY'),
        'base2',
        0.0,
        'https://images.unsplash.com/photo-1597415885008-d7929349a704?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1350&q=80',
        true),
       ('b6bcc88e-df82-11ea-87d0-0242ac130003',
        TO_DATE(cast('08-17-2020' as TEXT), 'MM-WW-YYYY'),
        TO_DATE(cast('08-17-2020' as TEXT), 'MM-WW-YYYY'),
        'base3',
        0.0,
        'https://images.unsplash.com/photo-1597055181449-b3f17e2ada63?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=700&q=80',
        true);

INSERT INTO base_member (id,
                         base_id,
                         member_id,
                         role)
VALUES ('f8aa999f-7544-45d2-83b7-5b9049c42e3a',
        (SELECT id from base where name='base1'),
        (SELECT id from tb_user where username = 'arcq'),
        'ADMIN'),
       ('d165cbf0-5198-4adf-83a3-2c136ccc01a5',
        (SELECT id from base where name='base1'),
        (SELECT id from tb_user where username = 'sita'),
        'MEMBER'),
       ('fbc5e224-2ea2-4984-abbc-67c0e3e3da90',
        (SELECT id from base where name = 'base1'),
        (SELECT id from tb_user where username = 'd0nkers'),
        'MEMBER'),
       ('15dcbeb4-e93c-4e61-b535-3575bcf9ec8c',
        (SELECT id from base where name = 'base2'),
        (SELECT id from tb_user where username = 'arcq'),
        'MEMBER'),
       ('9e81f2f1-71f9-403d-8e37-e83c7d5485c5',
        (SELECT id from base where name = 'base2'),
        (SELECT id from tb_user where username = 'sita'),
        'ADMIN'),
       ('824dcc06-f4c8-4a92-b833-4499d6c71577',
        (SELECT id from base where name = 'base3'),
        (SELECT id from tb_user where username = 'd0nkers'),
        'MEMBER'),
       ('ac11e3fe-d040-4d27-a24e-1b8c29ddd770',
        (SELECT id from base where name = 'base3'),
        (SELECT id from tb_user where username = 'arcq'),
        'ADMIN');
