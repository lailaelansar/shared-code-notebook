package dao;

import model.ProgrammingLanguage;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProgrammingLanguageDAO {
    public List<ProgrammingLanguage> getAllLanguages() {
        List<ProgrammingLanguage> languages = new ArrayList<>();
        String sql = "SELECT id, name FROM programming_language ORDER BY name";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ProgrammingLanguage language = new ProgrammingLanguage();
                language.setId(rs.getInt("id"));
                language.setName(rs.getString("name"));
                languages.add(language);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return languages;
    }

    public int findOrCreateByName(String name) {
        String select = "SELECT id FROM programming_language WHERE name = ?";
        String insert = "INSERT INTO programming_language(name) VALUES (?)";
        try (Connection conn = DBConnection.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(select)) {
                stmt.setString(1, name);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) return rs.getInt("id");
                }
            }
            try (PreparedStatement stmt = conn.prepareStatement(insert, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, name);
                int affected = stmt.executeUpdate();
                if (affected == 0) return -1;
                try (ResultSet keys = stmt.getGeneratedKeys()) {
                    if (keys.next()) return keys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean linkProjectLanguage(int projectId, int languageId) {
        String sql = "INSERT INTO project_language(project_id, programming_language_id) VALUES (?, ?) ON CONFLICT DO NOTHING";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, projectId);
            stmt.setInt(2, languageId);
            return stmt.executeUpdate() >= 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
