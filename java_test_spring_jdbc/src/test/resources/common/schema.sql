DROP TABLE IF EXISTS kv;
CREATE TABLE kv (
  k   varchar(255) not null,
  v   varchar(255) not null
);

DROP TABLE IF EXISTS persons;
CREATE TABLE persons (
  id    identity,
  name  varchar(255) not null
);

DROP TABLE IF EXISTS books;
CREATE TABLE books (
  id          identity,
  name        varchar(255) not null,
  isbn        char(12) not null,
  published   date not null
);