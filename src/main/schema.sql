CREATE TABLE Team(
    id serial primary key ,
    name varchar(200) not null,
    continent varchar(50)
);

CREATE TABLE Player(
    id serial primary key,
    name varchar(200) not null,
    age INT NOT NULL,
    position VARCHAR(50) NOT NULL,
    team_id INT REFERENCES team(id),
    goal_nb varchar(100)
);


insert into team values
    (1,'Real Madrid CF','EUROPA'),
    (2,'FC Bracelona','EUROPA'),
    (3,'Atlético de Madrid','EUROPA'),
    (4,'Al Ahly SC','AFRICA'),
    (5,'Inter Miami CF','AMERCICA');

insert into Player values
    (1,'Thibaut Courtois',32,'GK',1),
    (2,'Dani Carvajal',33,'DEF',1),
    (3,'Jude Belingham',21,'MIDF',1),
    (4,'Robert Lewandowski',36,'STR',2),
    (5,'Antoine Griezman',33,'STR',3);

ALTER TABLE player
    ADD COLUMN goal_nb INTEGER;
UPDATE player SET goal_nb = 0 WHERE name = 'Thibaut Courtois';
UPDATE player SET goal_nb = 2 WHERE name = 'Dani Carvajal';
UPDATE player SET goal_nb = 5 WHERE name = 'Jude Bellingham';
UPDATE player SET goal_nb = NULL WHERE name = 'Robert Lewandowski';
UPDATE player SET goal_nb = NULL WHERE name = 'Antoine Griezmann';
