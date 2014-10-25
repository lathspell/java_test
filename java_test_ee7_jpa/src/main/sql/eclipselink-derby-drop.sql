ALTER TABLE books DROP CONSTRAINT books_language_id
ALTER TABLE books DROP CONSTRAINT FK_books_author_id
ALTER TABLE book_to_bookstore DROP CONSTRAINT bktobookstorebokid
ALTER TABLE book_to_bookstore DROP CONSTRAINT bktbkstorebkstreid
DROP TABLE books
DROP TABLE languages
DROP TABLE authors
DROP TABLE book_to_bookstore
DROP TABLE bookstores
DROP TABLE not_null_example
DROP TABLE CASEEXAMPLE
DROP TABLE enum_example
