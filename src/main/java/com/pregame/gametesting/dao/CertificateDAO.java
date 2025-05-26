 package com.pregame.gametesting.dao;


 import com.pregame.gametesting.model.Certificate;
 import com.pregame.gametesting.model.Tester;

 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.sql.Statement;
 import java.util.ArrayList;
 import java.util.List;

 import static com.pregame.gametesting.util.DBConnectionManager.getConnection;

 public class CertificateDAO {

    private Certificate mapResultSetToCertificate(ResultSet rs) throws SQLException {
        Certificate certificate = new Certificate();
        certificate.setCertificateId(rs.getInt("CertificatesID")); // Matches schema
        certificate.setName(rs.getString("Name"));
        certificate.setCode(rs.getString("code"));   // Matches schema
        certificate.setField(rs.getString("field")); // Matches schema
        certificate.setTesterId(rs.getInt("TesterID")); // **** ADD THIS ****
        return certificate;
    }

    /**
     * Inserts a new certificate into the database and sets the auto-generated ID.
     * @param certificate The certificate to insert.
     * @return The generated CertificatesID if successful, -1 otherwise.
     * @throws SQLException if a database error occurs.
     */
    public int insertCertificate(Certificate certificate) throws SQLException {
        String sql = "INSERT INTO Certificates (Name, code, field, TesterID) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;
        int generatedId = -1;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, certificate.getName());
            stmt.setString(2, certificate.getCode());
            stmt.setString(3, certificate.getField());
            stmt.setInt(4, certificate.getTesterId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1);
                    certificate.setCertificateId(generatedId);
                }
            }
        } finally {
            closeResources(conn, stmt, generatedKeys);
        }

        return generatedId;
    }

    public Certificate getCertificateById(int certificateId) throws SQLException {
        String sql = "SELECT CertificatesID, Name, code, field, TesterID FROM Certificates WHERE CertificatesID = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Certificate certificate = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, certificateId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                certificate = this.mapResultSetToCertificate(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find Certificate", e);
        } finally {
            closeResources(conn, stmt, rs);
        }

        return certificate;
    }

    public List<Certificate> getCertificatesByTesterId(int testerId) throws SQLException {
        List<Certificate> certificates = new ArrayList<>();
        String sql = "SELECT CertificatesID, Name, code, field, TesterID FROM Certificates WHERE TesterID = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, testerId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                certificates.add(this.mapResultSetToCertificate(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get certificates by tester ID", e);
        } finally {
            closeResources(conn, stmt, rs);
        }

        return certificates;
    }

    public List<Certificate> getAllCertificates() throws SQLException {
        List<Certificate> certificates = new ArrayList<>();
        String sql = "SELECT CertificatesID, Name, code, field, TesterID FROM Certificates";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                certificates.add(this.mapResultSetToCertificate(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get all certificates", e);
        } finally {
            closeResources(conn, stmt, rs);
        }

        return certificates;
    }

    public boolean updateCertificate(Certificate certificate) throws SQLException {
        String sql = "UPDATE Certificates SET Name=?, code=?, field=?, TesterID=? WHERE CertificatesID=?";
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, certificate.getName());
            stmt.setString(2, certificate.getCode());
            stmt.setString(3, certificate.getField());
            stmt.setInt(4, certificate.getTesterId());
            stmt.setInt(5, certificate.getCertificateId());
            success = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update certificate", e);
        } finally {
            closeResources(conn, stmt, null);
        }

        return success;
    }

    public boolean deleteCertificate(int certificateId) throws SQLException {
        String sql = "DELETE FROM Certificates WHERE CertificatesID=?";
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, certificateId);
            success = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete certificate", e);
        } finally {
            closeResources(conn, stmt, null);
        }

        return success;
    }

    public List<Certificate> getCertificatesByField(String field) throws SQLException {
        List<Certificate> certificates = new ArrayList<>();
        String sql = "SELECT CertificatesID, Name, code, field, TesterID FROM Certificates WHERE field = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, field);
            rs = stmt.executeQuery();

            while (rs.next()) {
                certificates.add(this.mapResultSetToCertificate(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get certificates by field", e);
        } finally {
            closeResources(conn, stmt, rs);
        }

        return certificates;
    }

     protected void closeStatement(PreparedStatement stmt) {
         if (stmt != null) {
             try {
                 stmt.close();
             } catch (SQLException e) {
                 System.err.println("Error closing Statement: " + e.getMessage());
             }
         }
     }

     protected void closeConnection(Connection conn) {
         if (conn != null) {
             try {
                 conn.close();
             } catch (SQLException e) {
                 System.err.println("Error closing Connection: " + e.getMessage());
             }
         }
     }

     protected void closeResources(Connection conn, PreparedStatement stmt, ResultSet rs) {
         closeResultSet(rs);
         closeStatement(stmt);
         closeConnection(conn);
     }
     protected void closeResultSet(ResultSet rs) {
         if (rs != null) {
             try {
                 rs.close();
             } catch (SQLException e) {
                 System.err.println("Error closing ResultSet: " + e.getMessage());
             }
         }
     }
}
