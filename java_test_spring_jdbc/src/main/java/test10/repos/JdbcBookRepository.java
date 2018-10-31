package test10.repos;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import common.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class JdbcBookRepository implements BookRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Book findById(long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM books WHERE id = ?", new BeanPropertyRowMapper<>(Book.class), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Book save(Book book) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate).withTableName("books").usingGeneratedKeyColumns("id");
        Map<String, Object> map = new ObjectMapper().registerModule(new JavaTimeModule()).convertValue(book, new TypeReference<Map<String, Object>>() {
        });
        long newId = insert.executeAndReturnKey(map).longValue();
        return findById(newId); // refreshed in case of SQL Triggers
    }
}
