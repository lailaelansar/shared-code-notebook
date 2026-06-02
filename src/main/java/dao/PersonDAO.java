package dao;

import model.Person;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Person> FULL_PERSON_MAPPER = (rs, rowNum) -> {
        Person person = new Person();
        person.setId(rs.getInt("id"));
        person.setFirstName(rs.getString("first_name"));
        person.setLastName(rs.getString("last_name"));
        person.setEmail(rs.getString("email"));
        person.setPasswordHash(rs.getString("password_hash"));
        person.setRole(rs.getString("role"));
        return person;
    };

    private static final RowMapper<Person> USER_MAPPER = (rs, rowNum) -> {
        Person person = new Person();
        person.setId(rs.getInt("id"));
        person.setFirstName(rs.getString("first_name"));
        person.setLastName(rs.getString("last_name"));
        person.setEmail(rs.getString("email"));
        person.setRole(rs.getString("role"));
        return person;
    };

    public boolean register(Person person) {
        String sql = "INSERT INTO person(first_name, last_name, email, password_hash, role) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                person.getFirstName(),
                person.getLastName(),
                person.getEmail(),
                person.getPasswordHash(),
                person.getRole()) == 1;
    }

    public Person login(String email, String passwordHash) {
        String sql = "SELECT id, first_name, last_name, email, password_hash, role FROM person WHERE email = ? AND password_hash = ?";
        List<Person> results = jdbcTemplate.query(sql, FULL_PERSON_MAPPER, email, passwordHash);
        return results.isEmpty() ? null : results.get(0);
    }

    public List<Person> getAllUsers() {
        String sql = "SELECT id, first_name, last_name, email, role FROM person ORDER BY id DESC";
        return jdbcTemplate.query(sql, USER_MAPPER);
    }

    public boolean hasUsers() {
        String sql = "SELECT COUNT(*) FROM person";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count != null && count > 0;
    }

    public Person getUserById(int id) {
        String sql = "SELECT id, first_name, last_name, email, role FROM person WHERE id = ?";
        List<Person> results = jdbcTemplate.query(sql, USER_MAPPER, id);
        return results.isEmpty() ? null : results.get(0);
    }

    public boolean updateUser(Person person) {
        String sql = "UPDATE person SET first_name = ?, last_name = ?, email = ?, role = ? WHERE id = ?";
        return jdbcTemplate.update(sql,
                person.getFirstName(),
                person.getLastName(),
                person.getEmail(),
                person.getRole(),
                person.getId()) == 1;
    }

    public boolean deleteUser(int id) {
        String sql = "DELETE FROM person WHERE id = ?";
        return jdbcTemplate.update(sql, id) == 1;
    }
}
