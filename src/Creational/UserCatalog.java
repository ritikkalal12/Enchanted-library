package Creational;

import java.sql.*;
import java.util.*;

public class UserCatalog {

    private static UserCatalog instance;
    private Connection connection;

    private final String DB_URL = "jdbc:mysql://localhost:3307/library";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "1234";

    private UserCatalog() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("✅ User DB connection successful.");
        } catch (SQLException e) {
            System.err.println("❌ Failed to connect to User DB: " + e.getMessage());
        }
    }

    public static synchronized UserCatalog getInstance() {
        if (instance == null) {
            instance = new UserCatalog();
        }
        return instance;
    }

    public void addUser(User user) {
        String query = "INSERT INTO Users (name, role) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getRole());
            stmt.executeUpdate();
            System.out.println("✅ User added to DB: " + user.getName());
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("⚠️ User already exists: " + user.getName());
        } catch (SQLException e) {
            System.err.println("❌ Error adding user: " + e.getMessage());
        }
    }

    public User getUser(String name) {
        String query = "SELECT * FROM Users WHERE name = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                switch (role) {
                    case "Librarian": return new Librarian(name);
                    case "Scholar": return new Scholar(name);
                    case "Guest": return new Guest(name);
                    default: return null;
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error retrieving user: " + e.getMessage());
        }

        return null;
    }

    public String listUsers() {
        StringBuilder sb = new StringBuilder();
        try {
            String sql = "SELECT * FROM users";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            sb.append(String.format("%-20s %-20s%n", "Username", "Role"));
            sb.append("--------------------------------------\n");

            while (rs.next()) {
                sb.append(String.format("%-20s %-20s%n", rs.getString("username"), rs.getString("role")));
            }
        } catch (SQLException e) {
            sb.append("Error retrieving users: ").append(e.getMessage());
        }
        return sb.toString();
    }

    public boolean registerUser(String name, String password, String role) {
        String query = "INSERT INTO Users (name, password, role) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, password);
            stmt.setString(3, role.toLowerCase());
            stmt.executeUpdate();
            return true;
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("⚠️ User already exists: " + name);
        } catch (SQLException e) {
            System.err.println("❌ Error registering user: " + e.getMessage());
        }
        return false;
    }

    public User authenticateUser(String name, String password) {
        String query = "SELECT * FROM Users WHERE name = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role").toLowerCase();
                switch (role) {
                    case "librarian": return new Librarian(name);
                    case "scholar": return new Scholar(name);
                    case "guest": return new Guest(name);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error authenticating user: " + e.getMessage());
        }
        return null;
    }

}
