alter table mission add base_id uuid not null
        constraint fk_base_chat_base_base_uuid
            references base;

alter table mission add points_required int4;
alter table mission add is_complete bool;

create table mission_question
(
    id uuid not null
        constraint mission_question_key
            primary key,
    mission uuid not null
        constraint fk_mission_question_mission_uuid
        references mission,
    question uuid not null
        constraint fk_mission_question_question_uuid
        references question,
    in_progress boolean not null,
    order int4
);
