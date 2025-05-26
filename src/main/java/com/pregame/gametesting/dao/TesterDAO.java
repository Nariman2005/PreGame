package com.pregame.gametesting.dao;


import com.pregame.gametesting.model.Tester;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.pregame.gametesting.util.DBConnectionManager.getConnection;


public class TesterDAO extends UserDAO {
    public TesterDAO() {
        // Empty constructor - no need to create connection in constructor
    }

    private static Tester mapResultSetToTester(ResultSet rs) throws SQLException {
        Tester tester = new Tester();
        tester.setTesterId(rs.getInt("TesterID"));
        tester.setName(rs.getString("Name"));
        tester.setPassword(rs.getString("Password"));
        tester.setAge(rs.getInt("Age"));
        tester.setCountryCode(rs.getString("CountryCode"));
        tester.setTelephone(rs.getString("Telephone"));
        tester.setEmail(rs.getString("email"));
        tester.setRank(rs.getString("Ranks"));
        return tester;
    }

    public int insertTester(Tester tester) throws SQLException {
        String sql = "INSERT INTO Tester (Name, Password, Age, CountryCode, Telephone, email, Ranks) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;
        int testerId = -1;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, tester.getName());
            stmt.setString(2, tester.getPassword());
            stmt.setInt(3, tester.getAge());
            stmt.setString(4, tester.getCountryCode());
            stmt.setString(5, tester.getTelephone());
            stmt.setString(6, tester.getEmail());
            stmt.setString(7, tester.getRank());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    testerId = generatedKeys.getInt(1);
                    tester.setTesterId(testerId);
                }
            }
        } finally {
            closeResources(conn, stmt, generatedKeys);
        }

        return testerId;
    }



    public static Tester getTesterById(int testerId) throws SQLException {
        String sql = "SELECT * FROM Tester WHERE TesterID = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Tester tester = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, testerId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                tester = mapResultSetToTester(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find Tester", e);
        } finally {
            closeResources(conn, stmt, rs);
        }

        return tester;
    }

    public List<Tester> getAllTesters() throws SQLException {
        List<Tester> testers = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            String sql = "SELECT * FROM Tester";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while(rs.next()) {
                testers.add(this.mapResultSetToTester(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("getAllTesters Failed", e);
        } finally {
            closeResources(conn, stmt, rs);
        }

        return testers;
    }

    public boolean updateTester(Tester tester) throws SQLException {
        String sql = "UPDATE Tester SET Name=?, Password=?, Age=?, CountryCode=?, Telephone=?, email=?, Ranks=? WHERE TesterID=?";
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, tester.getName());
            stmt.setString(2, tester.getPassword());
            stmt.setInt(3, tester.getAge());
            stmt.setString(4, tester.getCountryCode());
            stmt.setString(5, tester.getTelephone());
            stmt.setString(6, tester.getEmail());
            stmt.setString(7, tester.getRank());
            stmt.setInt(8, tester.getTesterId());
            success = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Update Failed", e);
        } finally {
            closeResources(conn, stmt, null);
        }

        return success;
    }

    public boolean deleteTester(int testerId) throws SQLException {
        String sql = "DELETE FROM Tester WHERE TesterID=?";
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, testerId);
            success = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed To delete Tester", e);
        } finally {
            closeResources(conn, stmt, null);
        }

        return success;
    }

    public Tester getTesterByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM Tester WHERE email = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Tester tester = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            rs = stmt.executeQuery();

            if (rs.next()) {
                tester = this.mapResultSetToTester(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed To getTesterByEmail", e);
        } finally {
            closeResources(conn, stmt, rs);
        }

        return tester;
    }

    /**
     * Get tester by email and password for authentication
     *
     * @param email Tester's email
     * @param password Tester's password
     * @return Tester object if authentication successful, null otherwise
     */
    public Tester getTesterByEmailAndPassword(String email, String password) throws SQLException {
        String sql = "SELECT * FROM Tester WHERE email = ? AND Password = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Tester tester = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            rs = stmt.executeQuery();

            if (rs.next()) {
                tester = this.mapResultSetToTester(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to authenticate tester", e);
        } finally {
            closeResources(conn, stmt, rs);
        }

        return tester;
    }

}
