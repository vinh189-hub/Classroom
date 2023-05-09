create table if not exists files
(
    id      serial primary key,
    post_id integer,
    url     varchar
)