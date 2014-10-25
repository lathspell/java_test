ALTER TABLE books DROP FOREIGN KEY FK_books_language_id
ALTER TABLE books DROP FOREIGN KEY FK_books_author_id
ALTER TABLE book_to_bookstore DROP FOREIGN KEY FK_book_to_bookstore_book_id
ALTER TABLE book_to_bookstore DROP FOREIGN KEY FK_book_to_bookstore_bookstore_id
DROP TABLE books
DROP TABLE languages
DROP TABLE authors
DROP TABLE book_to_bookstore
DROP TABLE bookstores
