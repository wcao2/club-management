 CREATE TABLE clubs (
    id                BIGSERIAL  PRIMARY KEY,
    name              VARCHAR(30) not null unique,
    description       VARCHAR(150),
    location          VARCHAR(100),
    start_date	      date default CURRENT_DATE
);

CREATE TABLE players (
    id              BIGSERIAL ,
    name            VARCHAR(30) not null unique,
    email           VARCHAR(50),
    address         VARCHAR(150),
    hired_date      date default CURRENT_DATE,
    position        varchar(20),
    club_id         BIGINT NOT NULL
);
ALTER TABLE players ADD CONSTRAINT players_pk PRIMARY KEY ( id );

CREATE TABLE accounts(
	id 		BIGSERIAL PRIMARY KEY ,
	bank_name       VARCHAR(30),
	account_type    VARCHAR(30),
	balance		NUMERIC(10,2),
	player_id         BIGINT NOT NULL
);

alter table accounts ADD CONSTRAINT accounts_players_fk foreign key (player_id) REFERENCES players(id) on delete cascade;

alter table players ADD CONSTRAINT players_clubs_fk foreign key (club_id) REFERENCES clubs(id)  on delete cascade;