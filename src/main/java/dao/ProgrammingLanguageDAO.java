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
}
