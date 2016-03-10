package de.lathspell.test.springboot.service;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import de.lathspell.test.springboot.model.Person;

@Service
public class PersonService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int addPerson(Person person) {
        return jdbcTemplate.update("INSERT INTO person(first_name, last_name, age) VALUES (?,?,?)",
                person.getFirstName(), person.getLastName(), person.getAge());
    }

    public List<Person> getAllPerson() {
        // queryForList() might work as well if the column names correspond
        return jdbcTemplate.query("SELECT first_name, last_name, age FROM person", (ResultSet rs, int arg1) -> {
            Person p = new Person();
            p.setAge(rs.getInt("age"));
            p.setFirstName(rs.getString("first_name"));
            p.setLastName(rs.getString("last_name"));
            return p;
        });
    }

    public List<Map<String, Object>> getAllPerson2() {
        return jdbcTemplate.queryForList("SELECT first_name, last_name, age FROM person");
    }
}
