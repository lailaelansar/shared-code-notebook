package dao;

import model.Project;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO {
    public boolean createProject(Project project) {
        String sql = "INSERT INTO project(owner_id, title, description, completed) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, project.getOwnerId());
            stmt.setString(2, project.getTitle());
            stmt.setString(3, project.getDescription());
            stmt.setBoolean(4, project.isCompleted());
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Project> getProjectsByUser(int userId) {
        return new ArrayList<>();
    }

    public boolean updateProject(Project project) {
        return false;
    }

    public boolean deleteProject(int projectId) {
        return false;
    }
}
