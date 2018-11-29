/*
DROP TABLE IF EXISTS team;
DROP TABLE IF EXISTS person;

CREATE TABLE team (
  id            int not null primary key,
  --
  name          text
);

CREATE TABLE person (
  id            int not null primary key,
  --
  first_name    varchar(150),
  last_name     varchar(150),
  year_of_birth int,
  place         varchar(100),
  --
  team_id       int
);

ALTER TABLE person ADD CONSTRAINT fk_team_id FOREIGN KEY (team_id) REFERENCES team (id);
*/