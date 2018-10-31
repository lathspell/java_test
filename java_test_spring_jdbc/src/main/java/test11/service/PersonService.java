package test11.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.model.Kv;
import common.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PersonService {

    private final JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert personInsert;

    private final ObjectMapper om;

    @Autowired
    public PersonService(JdbcTemplate jdbcTemplate, ObjectMapper om) {
        this.jdbcTemplate = jdbcTemplate;
        this.om = om;

        personInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("persons").usingGeneratedKeyColumns("id");
    }

    public long save(Person person) {
        Map<String, Object> map = om.convertValue(person, Map.class);
        return personInsert.executeAndReturnKey(map).longValue();
    }

    public Person findByName(String name) {
        try {
            return jdbcTemplate.queryForObject("SELECT id, name FROM persons WHERE name = ?", new BeanPropertyRowMapper<Person>(Person.class), name);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
