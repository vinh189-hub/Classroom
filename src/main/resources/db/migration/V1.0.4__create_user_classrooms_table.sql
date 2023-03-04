CREATE TABLE "users_classrooms"
(
    "users_id"      serial,
    "classrooms_id" int,
    "role"          int
);

ALTER TABLE "users_classrooms"
    ADD FOREIGN KEY ("users_id") REFERENCES "users" ("id");

ALTER TABLE "users_classrooms"
    ADD FOREIGN KEY ("classrooms_id") REFERENCES "classrooms" ("id");