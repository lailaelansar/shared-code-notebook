package dao;

import model.ProgrammingLanguage;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ProgrammingLanguageDAO {

    private final JdbcTemplate jdbcTemplate;

    public ProgrammingLanguageDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<ProgrammingLanguage> LANGUAGE_MAPPER = (rs, rowNum) -> {
        ProgrammingLanguage language = new ProgrammingLanguage();
        language.setId(rs.getInt("id"));
        language.setName(rs.getString("name"));
        return language;
    };

    public List<ProgrammingLanguage> getAllLanguages() {
        String sql = "SELECT id, name FROM programming_language ORDER BY name";
        return jdbcTemplate.query(sql, LANGUAGE_MAPPER);
    }

    public int findOrCreateByName(String name) {
        String select = "SELECT id FROM programming_language WHERE name = ?";
        List<Integer> existing = jdbcTemplate.query(select, (rs, rowNum) -> rs.getInt("id"), name);
        if (!existing.isEmpty()) {
            return existing.get(0);
        }

        String insert = "INSERT INTO programming_language(name) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int affected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insert, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            return ps;
        }, keyHolder);

        if (affected == 0 || keyHolder.getKey() == null) {
            return -1;
        }
        return keyHolder.getKey().intValue();
    }

    public boolean linkProjectLanguage(int projectId, int languageId) {
        String sql = "INSERT INTO project_language(project_id, programming_language_id) VALUES (?, ?) ON CONFLICT DO NOTHING";
        jdbcTemplate.update(sql, projectId, languageId);
        return true;
    }
}
