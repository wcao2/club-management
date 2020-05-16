CREATE TABLE users (
    id              BIGSERIAL NOT NULL,
    name            VARCHAR(30) not null unique,
    password        VARCHAR(64),
    secret_key      varchar(512),
    first_name      VARCHAR(30),
    last_name       VARCHAR(30),
    email           VARCHAR(50) not null unique
);
ALTER TABLE users ADD CONSTRAINT users_pk PRIMARY KEY ( id );