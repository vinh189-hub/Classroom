CREATE TABLE users
(
    "id"         serial primary key ,
    "email"      varchar unique ,
    "password"   varchar,
    "status"     int,
    "username"   varchar,
    "created_at" timestamp,
    "updated_at" timestamp
);