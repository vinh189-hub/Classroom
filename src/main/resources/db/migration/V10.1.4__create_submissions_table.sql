create table submissions(
    id  serial primary key ,
    user_id integer,
    assignment_id integer,
    submission_date timestamp,
    created_at timestamp,
    updated_at timestamp
)