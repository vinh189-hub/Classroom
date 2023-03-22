CREATE TABLE "profiles"
(
    "id"         SERIAL PRIMARY KEY,
    "user_id"    bigint,
    "avatar"     varchar,
    "created_at" timestamp,
    "updated_at" timestamp
);