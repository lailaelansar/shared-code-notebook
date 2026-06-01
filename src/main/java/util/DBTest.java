package util;

import java.sql.Connection;

public class DBTest {
    public static void main(String[] args) {
        System.out.println("Trying to obtain DB connection using src/main/resources/db.properties...");
        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("SUCCESS: Connected to the database.");
            } else {
                System.out.println("FAILED: Connection is null or closed.");
            }
        } catch (Exception e) {
            System.out.println("ERROR: Could not connect to the database:");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
