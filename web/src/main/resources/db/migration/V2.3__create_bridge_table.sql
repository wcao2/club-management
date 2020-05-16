CREATE TABLE users_roles (
    user_id    BIGINT NOT NULL,
    role_id    BIGINT NOT NULL
);

ALTER TABLE users_roles
    ADD CONSTRAINT users_fk FOREIGN KEY ( user_id )
        REFERENCES users ( id );
ALTER TABLE users_roles
    ADD CONSTRAINT role_fk FOREIGN KEY ( role_id )
        REFERENCES roles ( id );

insert into users_roles values
(1, 1),
(2, 2),
(3, 3),
(1, 2),
(1, 3)
;