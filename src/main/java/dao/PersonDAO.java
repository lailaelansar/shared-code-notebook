package dao;

import model.Person;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonDAO {
    public boolean register(Person person) {
        String sql = "INSERT INTO person(first_name, last_name, email, password_hash, role) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            statement.setString(3, person.getEmail());
            statement.setString(4, person.getPasswordHash());
            statement.setString(5, person.getRole());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Person login(String email, String passwordHash) {
        String sql = "SELECT id, first_name, last_name, email, password_hash, role FROM person WHERE email = ? AND password_hash = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            statement.setString(2, passwordHash);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Person person = new Person();
                person.setId(resultSet.getInt("id"));
                person.setFirstName(resultSet.getString("first_name"));
                person.setLastName(resultSet.getString("last_name"));
                person.setEmail(resultSet.getString("email"));
                person.setPasswordHash(resultSet.getString("password_hash"));
                person.setRole(resultSet.getString("role"));
                return person;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
