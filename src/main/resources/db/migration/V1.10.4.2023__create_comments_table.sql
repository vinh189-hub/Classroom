create table comments
(
    id      serial primary key,
    post_id integer,
    user_id integer,
    content varchar,
    created_at   timestamp,
    updated_at   timestamp
)