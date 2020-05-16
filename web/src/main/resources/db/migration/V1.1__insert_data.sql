INSERT INTO clubs(id,name,description,location) values(1, 'Bayern Munich', 'The famous soccer club','Germany');

INSERT INTO clubs(id,name,description,location) values(2, 'Manchester City', 'have a good club recent','England');

INSERT INTO players(id,name,email,address,position,club_id) values(1,'Mullier','Mullier@gmu.edu','9871_SUN_ROAD', 'forward',1);

INSERT INTO players(id,name,email,address,position,club_id) values(2,'Klose','Klose@gmu.edu','9871_SUN_ROAD', 'forward',1);

INSERT INTO accounts(id,bank_name,account_type,balance,player_id) values(1,'bank of America','Debit',9000.12,1);
INSERT INTO accounts(id,bank_name,account_type,balance,player_id) values(2,'bank of America','Debit',8000.12,2);
INSERT INTO accounts(id,bank_name,account_type,balance,player_id) values(3,'bank of America','Debit',7000.12,2);
INSERT INTO accounts(id,bank_name,account_type,balance,player_id) values(4,'bank of America','Debit',6000.12,1);