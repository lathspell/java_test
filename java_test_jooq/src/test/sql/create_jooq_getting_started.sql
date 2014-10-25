DROP DATABASE java_test_jooq;
CREATE DATABASE java_test_jooq CHARACTER SET utf8;
USE java_test_jooq;

DROP TABLE IF EXISTS book_to_boot_store;
DROP TABLE IF EXISTS author;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS book_store;
DROP TABLE IF EXISTS language;

CREATE TABLE language (
  id              int unsigned     NOT NULL auto_increment PRIMARY KEY,
  cd              CHAR(2)       NOT NULL,
  description     VARCHAR(50)
);

CREATE TABLE author (
  id              int unsigned     NOT NULL auto_increment PRIMARY KEY,
  first_name      VARCHAR(50),
  last_name       VARCHAR(50)  NOT NULL,
  date_of_birth   DATE,
  year_of_birth   int unsigned,
  distinguished   int(1) not null default -1
);

CREATE TABLE book (
  id              int unsigned     NOT NULL auto_increment PRIMARY KEY,
  author_id       int unsigned     NOT NULL,
  title           VARCHAR(100)     NOT NULL,
  published_in    int unsigned     NOT NULL,
  language_id     int unsigned     NOT NULL,
  status          enum("sold_out", "in_stock") not null default 'in_stock',

  CONSTRAINT fk_book_author     FOREIGN KEY (author_id)   REFERENCES author(id),
  CONSTRAINT fk_book_language   FOREIGN KEY (language_id) REFERENCES language(id)
);

CREATE TABLE book_store (
  name            VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE book_to_book_store (
  name            varchar(255) NOT NULL,
  book_id         int unsigned NOT NULL,
  stock           int unsigned not null,

  PRIMARY KEY (name, book_id),
  FOREIGN KEY (name)        REFERENCES book_store (name) ON DELETE CASCADE,
  FOREIGN KEY (book_id)     REFERENCES book (id)         ON DELETE CASCADE
);

CREATE TABLE bug (
  id            int unsigned NOT NULL auto_increment PRIMARY KEY,
  foo int,
  bar int not null default 42
);
