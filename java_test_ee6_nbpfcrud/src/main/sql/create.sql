SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS book_to_bookstore;
DROP TABLE IF EXISTS bookstores;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS authors;
DROP TABLE IF EXISTS languages;
SET FOREIGN_KEY_CHECKS=1;

CREATE TABLE languages (
  id            int unsigned NOT NULL AUTO_INCREMENT,
  --
  code          char(2) NOT NULL,
  name          varchar(50) DEFAULT NULL,
  --
  PRIMARY KEY (id),
  KEY (code),
  KEY (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE authors (
  id            int unsigned NOT NULL AUTO_INCREMENT,
  --
  first_name    varchar(50) not NULL,
  last_name     varchar(50) not NULL,
  date_of_birth date DEFAULT NULL,
  year_of_birth int unsigned DEFAULT NULL,
  distinguished int(1) DEFAULT 0,
  --
  PRIMARY KEY (id),
  UNIQUE KEY (first_name, last_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE books (
  id            int unsigned NOT NULL AUTO_INCREMENT,
  --
  author_id     int unsigned NOT NULL,
  title         varchar(100) NOT NULL,
  published_in  date default NULL,
  language_id   int unsigned NOT NULL,
  status        enum('SOLD_OUT','IN_STOCK') default 'IN_STOCK' NOT NULL,
  --
  PRIMARY KEY (id),
  KEY (author_id),
  KEY (language_id),
  FOREIGN KEY (author_id) REFERENCES authors (id),
  FOREIGN KEY (language_id) REFERENCES languages (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE bookstores (
  id            int unsigned not null auto_increment,
  --
  name          varchar(255) NOT NULL,
  category      tinyint not null default 10,
  --
  PRIMARY KEY (id),
  UNIQUE KEY (name),
  KEY (category)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE book_to_bookstore (
  id            int unsigned not null auto_increment,
  --
  book_id       int unsigned NOT NULL,
  bookstore_id  int unsigned not null,
  stock         int unsigned NOT NULL,
  created_at    datetime not null default 0,
  updated_at    timestamp not null default current_timestamp on update current_timestamp,
  --
  PRIMARY KEY (id),
  UNIQUE KEY (book_id, bookstore_id),
  FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE,
  FOREIGN KEY (bookstore_id) REFERENCES bookstores (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


