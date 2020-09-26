INSERT INTO tb_user (id,
                     created_at,
                     updated_at,
                     auth_key,
                     email,
                     first_name,
                     last_name,
                     image_url,
                     score,
                     username
) VALUES ('40e6215d-b5c6-4896-987c-f30f3678f608',
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
                 is_active
) VALUES ('6034f8e2-df82-11ea-87d0-0242ac130003',
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

INSERT INTO base_member (base_id,
                  member_id,
                  role)
VALUES ((SELECT id from base where name='base1'),
        (SELECT id from tb_user where username='arcq'),
        'ADMIN');

INSERT INTO base_member (base_id,
                         member_id,
                         role)
VALUES ((SELECT id from base where name='base1'),
        (SELECT id from tb_user where username='sita'),
        'MEMBER');

INSERT INTO base_member (base_id,
                         member_id,
                         role)
VALUES ((SELECT id from base where name='base1'),
        (SELECT id from tb_user where username='d0nkers'),
        'MEMBER');

INSERT INTO base_member (base_id,
                         member_id,
                         role)
VALUES ((SELECT id from base where name='base2'),
        (SELECT id from tb_user where username='arcq'),
        'MEMBER');

INSERT INTO base_member (base_id,
                         member_id,
                         role)
VALUES ((SELECT id from base where name='base2'),
        (SELECT id from tb_user where username='sita'),
        'ADMIN');


INSERT INTO base_member (base_id,
                         member_id,
                         role)
VALUES ((SELECT id from base where name='base3'),
        (SELECT id from tb_user where username='d0nkers'),
        'MEMBER');

INSERT INTO base_member (base_id,
                         member_id,
                         role)
VALUES ((SELECT id from base where name='base3'),
        (SELECT id from tb_user where username='arcq'),
        'ADMIN');
