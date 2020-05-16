CREATE TABLE images (
    id              BIGSERIAL NOT NULL,
    user_id         bigint not null,
    file_name       VARCHAR(64),
    s3key           varchar(512),
    creation_time   TIMESTAMP
);
ALTER TABLE images ADD CONSTRAINT images_pk PRIMARY KEY ( id );

alter table images
ADD CONSTRAINT FK_IMAGES_USER
foreign key (user_id) references users(id)  on delete cascade;