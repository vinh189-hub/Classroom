create table marks(
    id  serial primary key ,
    user_id integer,
    assignment_id integer,
    score varchar,
    created_at timestamp,
    updated_at timestamp
)