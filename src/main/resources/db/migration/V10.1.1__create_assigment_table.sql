create table assignments(
    id serial primary key,
    title   varchar,
    description varchar,
    score   varchar,
    deadline_date timestamp,
    created_at timestamp,
    updated_at timestamp
)