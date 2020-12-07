create table mission_question
(
    id uuid not null
        constraint mission_question_key
            primary key,
    mission_id uuid not null
        constraint fk_mission_question_mission_uuid
        references mission,
    question_id uuid not null
        constraint fk_mission_question_question_uuid
        references question,
    in_progress boolean not null,
    priority int4
);
