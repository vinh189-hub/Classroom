create table posts
(
    id           serial primary key,
    description  varchar,
    user_id      integer,
    classroom_id integer,
    created_at   timestamp,
    updated_at   timestamp
)