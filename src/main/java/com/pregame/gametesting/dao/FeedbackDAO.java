package com.pregame.gametesting.dao;

import com.pregame.gametesting.model.Feedback;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types; // For setting nulls
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import static com.pregame.gametesting.util.DBConnectionManager.getConnection;

public class FeedbackDAO {

    private Feedback mapResultSetToFeedback(ResultSet rs) throws SQLException {
        Feedback feedback = new Feedback();
        feedback.setFeedbackId(rs.getInt("Feedback_ID"));
        feedback.setGamerId(rs.getInt("Gamer_ID"));
        feedback.setGameId(rs.getInt("Game_ID"));
        feedback.setFeedbackText(rs.getString("FeedBackText"));
        feedback.setAttachments(rs.getBytes("Attachments"));
        feedback.setFeedbackDate(rs.getDate("FeedBackDate"));

        // Get email if it exists in the result set
        try {
            feedback.setEmail(rs.getString("Email"));
        } catch (SQLException e) {
            // Email column might not be in the result set, ignore the error
        }

        // Try to get Rating if it exists, otherwise default to 0
        try {
            feedback.setRating(rs.getInt("Rating"));
        } catch (SQLException e) {
            // Rating column doesn't exist, default to 0
            feedback.setRating(0);
        }

        // Handle categories (stored as comma-separated string in database)


        return feedback;
    }

    public static boolean insertFeedback(Feedback feedback) throws SQLException {
        String sql = "INSERT INTO Feedback (Gamer_ID, Game_ID, FeedBackText, Attachments, FeedBackDate, Email) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;
    
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
    
            stmt.setInt(1, feedback.getGamerId());
            stmt.setInt(2, feedback.getGameId());
            stmt.setString(3, feedback.getFeedbackText());
    
            if (feedback.getAttachments() != null) {
                stmt.setBytes(4, feedback.getAttachments());
            } else {
                stmt.setNull(4, Types.BLOB);
            }
    
            if (feedback.getFeedbackDate() != null) {
                stmt.setDate(5, new java.sql.Date(feedback.getFeedbackDate().getTime()));
            } else {
                stmt.setNull(5, Types.DATE);
            }
            
            // Add email parameter
            if (feedback.getEmail() != null) {
                stmt.setString(6, feedback.getEmail());
            } else {
                stmt.setNull(6, Types.VARCHAR);
            }

            int affectedRows = stmt.executeUpdate();
            success = affectedRows > 0;
        } finally {
            closeResources(conn, stmt, null);
        }
        return success;
    }

    public Feedback getFeedback(int gamerId, int gameId) throws SQLException {
        String sql = "SELECT Gamer_ID, Game_ID, FeedBackText, Attachments, FeedBackDate, Email FROM Feedback WHERE Gamer_ID = ? AND Game_ID = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Feedback feedback = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, gamerId);
            stmt.setInt(2, gameId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                feedback = this.mapResultSetToFeedback(rs);
            }
        } finally {
            closeResources(conn, stmt, rs);
        }
        return feedback;
    }

    public List<Feedback> getFeedbacksByGame(int gameId) throws SQLException {
        List<Feedback> feedbacks = new ArrayList<>();
        String sql = "SELECT Gamer_ID, Game_ID, FeedBackText, Attachments, FeedBackDate, Email FROM Feedback WHERE Game_ID = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, gameId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                feedbacks.add(this.mapResultSetToFeedback(rs));
            }
        } finally {
            closeResources(conn, stmt, rs);
        }
        return feedbacks;
    }

    public List<Feedback> getFeedbacksByGamer(int gamerId) throws SQLException {
        List<Feedback> feedbacks = new ArrayList<>();
        String sql = "SELECT Gamer_ID, Game_ID, FeedBackText, Attachments, FeedBackDate, Email FROM Feedback WHERE Gamer_ID = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, gamerId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                feedbacks.add(this.mapResultSetToFeedback(rs));
            }
        } finally {
            closeResources(conn, stmt, rs);
        }
        return feedbacks;
    }

    public boolean updateFeedback(Feedback feedback) throws SQLException {
        String sql = "UPDATE Feedback SET FeedBackText=?, Attachments=?, FeedBackDate=? WHERE Gamer_ID=? AND Game_ID=?";
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, feedback.getFeedbackText());
            if (feedback.getAttachments() != null) {
                stmt.setBytes(2, feedback.getAttachments());
            } else {
                stmt.setNull(2, Types.BLOB);
            }
            if (feedback.getFeedbackDate() != null) {
                stmt.setDate(3, new java.sql.Date(feedback.getFeedbackDate().getTime()));
            } else {
                stmt.setNull(3, Types.DATE);
            }
            stmt.setInt(4, feedback.getGamerId());
            stmt.setInt(5, feedback.getGameId());

            success = stmt.executeUpdate() > 0;
        } finally {
            closeResources(conn, stmt, null);
        }
        return success;
    }

    public boolean deleteFeedback(int gamerId, int gameId) throws SQLException {
        String sql = "DELETE FROM Feedback WHERE Gamer_ID=? AND Game_ID=?";
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, gamerId);
            stmt.setInt(2, gameId);
            success = stmt.executeUpdate() > 0;
        } finally {
            closeResources(conn, stmt, null);
        }
        return success;
    }

    public Feedback getFeedbackById(int feedbackId) throws SQLException {
        String sql = "SELECT Feedback_ID, Gamer_ID, Game_ID, FeedBackText, Attachments, FeedBackDate, Rating, Categories, Email "
                +
                "FROM Feedback WHERE Feedback_ID = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Feedback feedback = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, feedbackId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                feedback = mapResultSetToFeedback(rs);
            }
        } finally {
            closeResources(conn, stmt, rs);
        }
        return feedback;
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

    protected static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.err.println("Error closing ResultSet: " + e.getMessage());
            }
        }
    }

}
