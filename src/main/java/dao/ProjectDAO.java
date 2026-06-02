package dao;

import model.Project;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class ProjectDAO {

    private final JdbcTemplate jdbcTemplate;

    public ProjectDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Project> PROJECT_MAPPER = (rs, rowNum) -> {
        Project p = new Project();
        p.setId(rs.getInt("id"));
        p.setOwnerId(rs.getInt("owner_id"));
        p.setTitle(rs.getString("title"));
        p.setDescription(rs.getString("description"));
        p.setCompleted(rs.getBoolean("completed"));
        Timestamp ts = rs.getTimestamp("created_at");
        if (ts != null) {
            p.setCreatedAt(ts.toLocalDateTime());
        }
        String ownerName = rs.getString("first_name") + " " + rs.getString("last_name");
        p.setOwnerName(ownerName.trim());
        return p;
    };

    public boolean createProject(Project project) {
        String sql = "INSERT INTO project(owner_id, title, description, completed) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                project.getOwnerId(),
                project.getTitle(),
                project.getDescription(),
                project.isCompleted()) == 1;
    }

    public int createProjectReturnId(Project project) {
        String sql = "INSERT INTO project(owner_id, title, description, completed) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int affected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, project.getOwnerId());
            ps.setString(2, project.getTitle());
            ps.setString(3, project.getDescription());
            ps.setBoolean(4, project.isCompleted());
            return ps;
        }, keyHolder);
        if (affected == 0 || keyHolder.getKey() == null) {
            return -1;
        }
        return keyHolder.getKey().intValue();
    }

    public List<Project> getProjectsByUser(int userId) {
        String sql = "SELECT p.id, p.owner_id, p.title, p.description, p.completed, p.created_at, u.first_name, u.last_name " +
                "FROM project p LEFT JOIN person u ON p.owner_id = u.id WHERE p.owner_id = ? ORDER BY p.created_at DESC";
        return jdbcTemplate.query(sql, PROJECT_MAPPER, userId);
    }

    public List<Project> getAllProjects() {
        String sql = "SELECT p.id, p.owner_id, p.title, p.description, p.completed, p.created_at, u.first_name, u.last_name " +
                "FROM project p LEFT JOIN person u ON p.owner_id = u.id ORDER BY p.created_at DESC";
        return jdbcTemplate.query(sql, PROJECT_MAPPER);
    }

    public Project getProjectById(int projectId) {
        String sql = "SELECT p.id, p.owner_id, p.title, p.description, p.completed, p.created_at, u.first_name, u.last_name " +
                "FROM project p LEFT JOIN person u ON p.owner_id = u.id WHERE p.id = ?";
        List<Project> results = jdbcTemplate.query(sql, PROJECT_MAPPER, projectId);
        return results.isEmpty() ? null : results.get(0);
    }

    public boolean updateProject(Project project) {
        String sql = "UPDATE project SET title = ?, description = ?, completed = ? WHERE id = ?";
        return jdbcTemplate.update(sql,
                project.getTitle(),
                project.getDescription(),
                project.isCompleted(),
                project.getId()) == 1;
    }

    public boolean deleteProject(int projectId) {
        String sql = "DELETE FROM project WHERE id = ?";
        return jdbcTemplate.update(sql, projectId) == 1;
    }
}
