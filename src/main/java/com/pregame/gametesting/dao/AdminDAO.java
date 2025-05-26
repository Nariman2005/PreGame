

package com.pregame.gametesting.dao;

import com.pregame.gametesting.model.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.pregame.gametesting.util.DBConnectionManager.getConnection;

/**
 * Data Access Object for Admin operations.
 */
public class AdminDAO extends UserDAO {

    private Admin mapResultSetToAdmin(ResultSet rs) throws SQLException {
        Admin admin = new Admin();
        admin.setAdminId(rs.getInt("AdminID"));
        admin.setName(rs.getString("Name"));
        admin.setPassword(rs.getString("Password"));
        admin.setAge(rs.getInt("Age"));
        admin.setCountryCode(rs.getString("CountryCode"));
        admin.setTelephone(rs.getString("Telephone"));
        admin.setEmail(rs.getString("email"));
        admin.setRole(rs.getString("Role"));
        admin.setAccessLevel(rs.getString("AccessLevel"));
        return admin;
    }


    public int insertAdmin(Admin admin) throws SQLException {
        String sql = "INSERT INTO Admin (Name, Password, Age, CountryCode, Telephone, email, Role, AccessLevel) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;
        int adminId = -1;

        try {
            conn = getConnection();

            // Validate admin object to prevent null pointer exceptions
            if (admin == null) {
                throw new SQLException("Admin object cannot be null");
            }

            // Prepare statement
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // Set parameters with null checking
            stmt.setString(1, admin.getName());
            stmt.setString(2, admin.getPassword());
            stmt.setInt(3, admin.getAge());
            stmt.setString(4, admin.getCountryCode());
            stmt.setString(5, admin.getTelephone());
            stmt.setString(6, admin.getEmail());
            stmt.setString(7, admin.getRole());
            stmt.setString(8, admin.getAccessLevel());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    adminId = generatedKeys.getInt(1);
                    admin.setAdminId(adminId);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Error inserting Admin: " + e.getMessage());
            throw new RuntimeException("Failed to insert Admin", e);
        } finally {
            closeResources(conn, stmt, generatedKeys);
        }

        return adminId;
    }


    public Admin getAdminById(int adminId) throws SQLException {
        String sql = "SELECT * FROM Admin WHERE AdminID = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Admin admin = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, adminId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                admin = mapResultSetToAdmin(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find Admin", e);
        } finally {
            closeResources(conn, stmt, rs);
        }

        return admin;
    }

    /**
     * Get all admins from the database.
     *
     * @return List of all admins
     * @throws SQLException if a database error occurs
     */
    public List<Admin> getAllAdmins() throws SQLException {
        List<Admin> admins = new ArrayList<>();
        String sql = "SELECT * FROM Admin";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                admins.add(mapResultSetToAdmin(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get all Admins", e);
        } finally {
            closeResources(conn, stmt, rs);
        }

        return admins;
    }

    /**
     * Update an existing admin in the database.
     *
     * @param admin The admin to update
     * @return true if successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean updateAdmin(Admin admin) throws SQLException {
        String sql = "UPDATE Admin SET Name=?, Password=?, Age=?, CountryCode=?, Telephone=?, email=?, Role=?, AccessLevel=? WHERE AdminID=?";
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, admin.getName());
            stmt.setString(2, admin.getPassword());
            stmt.setInt(3, admin.getAge());
            stmt.setString(4, admin.getCountryCode());
            stmt.setString(5, admin.getTelephone());
            stmt.setString(6, admin.getEmail());
            stmt.setString(7, admin.getRole());
            stmt.setString(8, admin.getAccessLevel());
            stmt.setInt(9, admin.getAdminId());

            success = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update Admin", e);
        } finally {
            closeResources(conn, stmt, null);
        }

        return success;
    }

    /**
     * Delete an admin from the database.
     *
     * @param adminId The ID of the admin to delete
     * @return true if successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean deleteAdmin(int adminId) throws SQLException {
        String sql = "DELETE FROM Admin WHERE AdminID=?";
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, adminId);
            success = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete Admin", e);
        } finally {
            closeResources(conn, stmt, null);
        }

        return success;
    }

    /**
     * Get admin by email.
     *
     * @param email Admin's email
     * @return Admin object if found, null otherwise
     * @throws SQLException if a database error occurs
     */
    public Admin getAdminByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM Admin WHERE email = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Admin admin = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            rs = stmt.executeQuery();

            if (rs.next()) {
                admin = mapResultSetToAdmin(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get Admin by email", e);
        } finally {
            closeResources(conn, stmt, rs);
        }

        return admin;
    }

    /**
     * Get admin by email and password for authentication.
     *
     * @param email Admin's email
     * @param password Admin's password
     * @return Admin object if authentication successful, null otherwise
     * @throws SQLException if a database error occurs
     */
    public Admin getAdminByEmailAndPassword(String email, String password) throws SQLException {
        String sql = "SELECT * FROM Admin WHERE email = ? AND Password = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Admin admin = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            rs = stmt.executeQuery();

            if (rs.next()) {
                admin = mapResultSetToAdmin(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to authenticate admin", e);
        } finally {
            closeResources(conn, stmt, rs);
        }

        return admin;
    }
}