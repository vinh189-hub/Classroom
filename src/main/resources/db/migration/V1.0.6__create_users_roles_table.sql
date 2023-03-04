CREATE TABLE "users_roles"
(
    "users_id" serial,
    "roles_id" int,
    constraint fk_users foreign key (users_id)references users(id),
    constraint fk_roles foreign key (roles_id)references roles(id)

);
