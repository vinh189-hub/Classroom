-- CREATE TABLE "users_classrooms"
-- (
--     "users_id"      serial,
--     "classrooms_id" int,
--     "role"          int
-- );
--
-- ALTER TABLE "users_classrooms"
--     ADD FOREIGN KEY ("users_id") REFERENCES "users" ("id");
--
-- ALTER TABLE "users_classrooms"
--     ADD FOREIGN KEY ("classrooms_id") REFERENCES "classrooms" ("id");

create table user_classroom (
    id serial primary key ,
    user_id integer,
    classroom_id integer,
    role integer,
    created_at timestamp,
    updated_at timestamp
)