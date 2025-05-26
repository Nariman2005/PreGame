package com.pregame.gametesting.dao;

import com.pregame.gametesting.model.GameDeveloper;
import com.pregame.gametesting.util.DBConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.pregame.gametesting.util.DBConnectionManager.getConnection;

public class GameDeveloperDAO extends UserDAO {
    private GameDeveloper mapResultSetToGameDeveloper(ResultSet rs) throws SQLException {
        GameDeveloper developer = new GameDeveloper();
        developer.setGameDeveloperid(rs.getInt("GameDeveloperID"));
        developer.setCompanyName(rs.getString("CompanyName"));
        developer.setName(rs.getString("Name"));
        developer.setPassword(rs.getString("Password"));
        developer.setAge(rs.getInt("Age"));
        developer.setCountryCode(rs.getString("CountryCode"));
        developer.setTelephone(rs.getString("Telephone"));
        developer.setEmail(rs.getString("email"));
        return developer;
    }

    public int insertGameDeveloper(GameDeveloper developer) throws SQLException {
        String sql = "INSERT INTO GameDeveloper (CompanyName, Name, Password, Age, CountryCode, Telephone, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;
        int developerId = -1;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, developer.getCompanyName());
            stmt.setString(2, developer.getName());
            stmt.setString(3, developer.getPassword());
            stmt.setInt(4, developer.getAge());
            stmt.setString(5, developer.getCountryCode());
            stmt.setString(6, developer.getTelephone());
            stmt.setString(7, developer.getEmail());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    developerId = generatedKeys.getInt(1);
                    developer.setGameDeveloperid(developerId);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Insertion Failed", e);
        } finally {
            closeResources(conn, stmt, generatedKeys);
        }

        return developerId;
    }

    public GameDeveloper getGameDeveloperById(int developerId) throws SQLException {
        String sql = "SELECT * FROM GameDeveloper WHERE GameDeveloperID = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        GameDeveloper developer = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, developerId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                developer = this.mapResultSetToGameDeveloper(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find Game Developer", e);
        } finally {
            closeResources(conn, stmt, rs);
        }

        return developer;
    }

    public List<GameDeveloper> getAllGameDevelopers() throws SQLException {
        List<GameDeveloper> developers = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            String sql = "SELECT * FROM GameDeveloper";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                developers.add(this.mapResultSetToGameDeveloper(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("getAllGameDevelopers Failed", e);
        } finally {
            closeResources(conn, stmt, rs);
        }

        return developers;
    }

    public boolean updateGameDeveloper(GameDeveloper developer) throws SQLException {
        String sql = "UPDATE GameDeveloper SET CompanyName=?, Name=?, Password=?, Age=?, CountryCode=?, Telephone=?, email=? WHERE GameDeveloperID=?";
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, developer.getCompanyName());
            stmt.setString(2, developer.getName());
            stmt.setString(3, developer.getPassword());
            stmt.setInt(4, developer.getAge());
            stmt.setString(5, developer.getCountryCode());
            stmt.setString(6, developer.getTelephone());
            stmt.setString(7, developer.getEmail());
            stmt.setInt(8, developer.getGameDeveloperid());
            success = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Update Failed", e);
        } finally {
            closeResources(conn, stmt, null);
        }

        return success;
    }

    public boolean deleteGameDeveloper(int developerId) throws SQLException {
        String sql = "DELETE FROM GameDeveloper WHERE GameDeveloperID=?";
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, developerId);
            success = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed To delete Game Developer", e);
        } finally {
            closeResources(conn, stmt, null);
        }

        return success;
    }

    public GameDeveloper getGameDeveloperByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM GameDeveloper WHERE email = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        GameDeveloper developer = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            rs = stmt.executeQuery();

            if (rs.next()) {
                developer = this.mapResultSetToGameDeveloper(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed To getGameDeveloperByEmail", e);
        } finally {
            closeResources(conn, stmt, rs);
        }

        return developer;
    }

    public GameDeveloper getGameDeveloperByEmailAndPassword(String email, String password) throws SQLException {
        String sql = "SELECT * FROM GameDeveloper WHERE email = ? AND Password = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        GameDeveloper developer = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            rs = stmt.executeQuery();

            if (rs.next()) {
                developer = this.mapResultSetToGameDeveloper(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to authenticate game developer", e);
        } finally {
            closeResources(conn, stmt, rs);
        }

        return developer;
    }
//
//    public static void main(String[] args) {
//        GameDeveloperDAO dao = new GameDeveloperDAO();
//
//        try {
//            // Test 1: Create a new game developer
//            System.out.println("Testing game developer creation...");
//            GameDeveloper newDeveloper = new GameDeveloper();
//            newDeveloper.setCompanyName("Test Studios");
//            newDeveloper.setName("Test Developer");
//            newDeveloper.setPassword("dev123");
//            newDeveloper.setAge(35);
//            newDeveloper.setCountryCode("EG");
//            newDeveloper.setTelephone("+201098765432");
//            newDeveloper.setEmail("test.developer@teststudios.com");
//
//            // Insert the game developer
//            dao.insertGameDeveloper(newDeveloper);
//            System.out.println("Game developer created successfully");
//
//            // Test 2: Get all game developers
//            System.out.println("\nGetting all game developers:");
//            List<GameDeveloper> allDevelopers = dao.getAllGameDevelopers();
//            System.out.println("Found " + allDevelopers.size() + " game developers");
//
//            // Find the ID of our test developer for further operations
//            int developerId = -1;
//            for (GameDeveloper dev : allDevelopers) {
//                if (dev.getEmail().equals("test.developer@teststudios.com")) {
//                    developerId = dev.getGameDeveloperid();
//                    System.out.println("Our test developer has ID: " + developerId);
//                    break;
//                }
//            }
//
//            if (developerId != -1) {
//                // Test 3: Get developer by ID
//                System.out.println("\nGetting developer by ID " + developerId);
//                GameDeveloper retrievedDeveloper = dao.getGameDeveloperById(developerId);
//                if (retrievedDeveloper != null) {
//                    System.out.println("Retrieved developer: " + retrievedDeveloper.getName() +
//                            ", Company: " + retrievedDeveloper.getCompanyName() +
//                            ", Email: " + retrievedDeveloper.getEmail());
//
//                    // Test 4: Update developer
//                    System.out.println("\nUpdating developer...");
//                    retrievedDeveloper.setCompanyName("Updated Studios");
//                    boolean updateResult = dao.updateGameDeveloper(retrievedDeveloper);
//                    System.out.println("Update result: " + updateResult);
//
//                    // Verify update
//                    GameDeveloper updatedDeveloper = dao.getGameDeveloperById(developerId);
//                    System.out.println("After update - Company Name: " + updatedDeveloper.getCompanyName());
//
//                    // Test 5: Get developer by email
//                    System.out.println("\nGetting developer by email...");
//                    GameDeveloper emailDeveloper = dao.getGameDeveloperByEmail("test.developer@teststudios.com");
//                    if (emailDeveloper != null) {
//                        System.out.println("Found by email: " + emailDeveloper.getName() +
//                                ", Company: " + emailDeveloper.getCompanyName());
//                    } else {
//                        System.out.println("Developer not found by email");
//                    }
//
//                    // Test 6: Delete developer (commented out to prevent actual deletion)
//                /*
//                System.out.println("\nDeleting developer...");
//                boolean deleteResult = dao.deleteGameDeveloper(developerId);
//                System.out.println("Delete result: " + deleteResult);
//
//                // Verify delete
//                GameDeveloper shouldBeNull = dao.getGameDeveloperById(developerId);
//                System.out.println("After delete - Developer exists: " + (shouldBeNull != null));
//                */
//                } else {
//                    System.out.println("Could not retrieve developer by ID");
//                }
//            } else {
//                System.out.println("Could not find test developer in the database");
//            }
//
//        } catch (SQLException e) {
//            System.err.println("Test failed with error:");
//            e.printStackTrace();
//        }
//    }
}