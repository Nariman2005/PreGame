package com.pregame.gametesting.dao;

import com.pregame.gametesting.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.pregame.gametesting.util.DBConnectionManager.getConnection;


public class UserDAO {

    public User authenticate(String email, String password) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        User user = null;

        try {
            conn = getConnection();

            // First check if user exists in any user table
            String userTypeQuery = "SELECT 'GAMER' as user_type FROM gamer WHERE email = ? " +
                    "UNION SELECT 'DEVELOPER' as user_type FROM gamedeveloper WHERE email = ? " +
                    "UNION SELECT 'TESTER' as user_type FROM tester WHERE email = ? " +
                    "LIMIT 1";

            stmt = conn.prepareStatement(userTypeQuery);
            stmt.setString(1, email);
            stmt.setString(2, email);
            stmt.setString(3, email);

            rs = stmt.executeQuery();

            if (rs.next()) {
                String userType = rs.getString("user_type");
                System.out.println("Found user type: " + userType); // Debug log
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        System.err.println("Error closing ResultSet: " + e.getMessage());
                    }
                }

                // Based on the user type, query the specific user table
                switch (userType) {
                    case "GAMER":
                        GamerDAO gamerDAO = new GamerDAO();
                        user = gamerDAO.getGamerByEmailAndPassword(email, password);
                        break;
                    case "DEVELOPER":
                        GameDeveloperDAO devDAO = new GameDeveloperDAO();
                        user = devDAO.getGameDeveloperByEmailAndPassword(email, password);
                        break;
                    case "TESTER":
                        TesterDAO testerDAO = new TesterDAO();
                        user = testerDAO.getTesterByEmailAndPassword(email, password);
                        break;
                }
            } else {
                System.out.println("No user found with email: " + email); // Debug log
            }

        } catch (SQLException e) {
            System.err.println("Error authenticating user: " + e.getMessage());
            e.printStackTrace(); // Print full stack trace for debugging
        } finally {
            if (rs != null && stmt != null) {
                try {
                    rs.close();
                    stmt.close();
                } catch (SQLException e) {
                    System.err.println("Error closing resources: " + e.getMessage());
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Error closing Connection: " + e.getMessage());
                }
            }
        }

        return user;
    }


    public int registerUser(User user) throws SQLException {
        String userType = user.getUserType();

        // Validate email uniqueness before insertion
        if (emailExists(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        // Insert the user based on their type
        return insertUser(user, userType);
    }

    public boolean emailExists(String email) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean exists = false;

        try {
            conn = getConnection();

            String query = "SELECT 1 FROM gamer WHERE email = ? " +
                    "UNION SELECT 1 FROM gamedeveloper WHERE email = ? " +
                    "UNION SELECT 1 FROM tester WHERE email = ? " +
                    "LIMIT 1";

            stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, email);
            stmt.setString(3, email);

            rs = stmt.executeQuery();
            exists = rs.next();

        } catch (SQLException e) {
            System.err.println("Error checking if email exists: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Error closing Connection: " + e.getMessage());
                }
            }
        }

        return exists;
    }


    public User getUserById(int userId, String userType) throws SQLException {
        User user = null;

        switch (userType) {
            case User.TYPE_GAMER:
                GamerDAO gamerDAO = new GamerDAO();
                user = gamerDAO.getGamerById(userId);
                break;
            case User.TYPE_DEVELOPER:
                GameDeveloperDAO devDAO = new GameDeveloperDAO();
                user = devDAO.getGameDeveloperById(userId);
                break;
            case User.TYPE_TESTER:
                TesterDAO testerDAO = new TesterDAO();
                user = testerDAO.getTesterById(userId);
                break;
        }

        return user;
    }


    public boolean deleteUser(int userId, String userType) throws SQLException {
        boolean success = false;

        switch (userType) {
            case User.TYPE_GAMER:
                GamerDAO gamerDAO = new GamerDAO();
                success = gamerDAO.deleteGamer(userId);
                break;
            case User.TYPE_DEVELOPER:
                GameDeveloperDAO devDAO = new GameDeveloperDAO();
                success = devDAO.deleteGameDeveloper(userId);
                break;
            case User.TYPE_TESTER:
                TesterDAO testerDAO = new TesterDAO();
                success = testerDAO.deleteTester(userId);
                break;
            case User.TYPE_ADMIN:
                AdminDAO adminDAO = new AdminDAO();
                success = adminDAO.deleteAdmin(userId);
                break;
            default:
                throw new IllegalArgumentException("Invalid user type: " + userType);
        }

        return success;
    }


    public int insertUser(User user, String userType) throws SQLException {
        int userId = -1;

        switch (userType) {
            case User.TYPE_GAMER:
                if (!(user instanceof Gamer)) {
                    throw new IllegalArgumentException("User object must be of type Gamer when userType is GAMER");
                }
                GamerDAO gamerDAO = new GamerDAO();
                gamerDAO.insertGamer((Gamer) user);
                userId = ((Gamer) user).getGamerId();
                break;

            case User.TYPE_DEVELOPER:
                if (!(user instanceof GameDeveloper)) {
                    throw new IllegalArgumentException("User object must be of type GameDeveloper when userType is DEVELOPER");
                }
                GameDeveloperDAO devDAO = new GameDeveloperDAO();
                userId = devDAO.insertGameDeveloper((GameDeveloper) user);
                break;

            case User.TYPE_TESTER:
                if (!(user instanceof Tester)) {
                    throw new IllegalArgumentException("User object must be of type Tester when userType is TESTER");
                }
                TesterDAO testerDAO = new TesterDAO();
                userId = testerDAO.insertTester((Tester) user);
                break;

            case User.TYPE_ADMIN:
                if (!(user instanceof Admin)) {
                    throw new IllegalArgumentException("User object must be of type Admin when userType is ADMIN");
                }
                AdminDAO adminDAO = new AdminDAO();
                userId = adminDAO.insertAdmin((Admin) user);
                break;

            default:
                throw new IllegalArgumentException("Invalid user type: " + userType);
        }

        return userId;
    }

    protected static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.err.println("Error closing ResultSet: " + e.getMessage());
            }
        }
    }

    protected static void closeStatement(PreparedStatement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                System.err.println("Error closing Statement: " + e.getMessage());
            }
        }
    }

    protected static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing Connection: " + e.getMessage());
            }
        }
    }

    protected static void closeResources(Connection conn, PreparedStatement stmt, ResultSet rs) {
        closeResultSet(rs);
        closeStatement(stmt);
        closeConnection(conn);
    }

    public boolean isAdmin(String email, String password) {
        return email.equals("PregameDevs@gmail.com") && password.equals("12345678");
    }
}

