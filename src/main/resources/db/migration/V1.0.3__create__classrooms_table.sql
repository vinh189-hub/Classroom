CREATE TABLE "classrooms"
(
    "id"          INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    "name"        varchar,
    "code"        varchar,
    "description" varchar,
    "theme"       varchar,
    "created_at"  timestamp,
    "updated_at"  timestamp
);