package com.pregame.gametesting.dao;

import com.pregame.gametesting.model.Review;
import com.pregame.gametesting.util.DBConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TesterFeedbackDAO {

    public List<Review> getFeedbackForDeveloper(int gameDeveloperId) {
        List<Review> feedbackList = new ArrayList<>();
        // SQL to join Review, Game, and optionally Tester
        // We want reviews for games developed by a specific GameDeveloperID
        String sql = "SELECT r.ReviewID, r.Tester_ID, r.Game_ID, r.FeedBackText, r.Attachments, " +
                "r.ReviewDate, r.FeedbackType, r.Status, g.Title AS GameTitle " +
                // ", t.SomeTesterNameColumn AS TesterName " + // If you have a Tester table with a name
                "FROM Review r " +
                "JOIN Game g ON r.Game_ID = g.GameID " +
                // "LEFT JOIN Tester t ON r.Tester_ID = t.TesterID " + // Optional: if you want tester details
                "WHERE g.GameDeveloperID = ? " +
                "ORDER BY r.ReviewDate DESC, g.Title ASC";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, gameDeveloperId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Review feedback = new Review();
                feedback.setReviewId(rs.getInt("ReviewID"));

                // Handle nullable Tester_ID
                int testerId = rs.getInt("Tester_ID");
                if (rs.wasNull()) {
                    feedback.setTesterId(null);
                    feedback.setTesterIdentifier("N/A"); // Or some other placeholder
                } else {
                    feedback.setTesterId(testerId);
                    feedback.setTesterIdentifier("Tester #" + testerId); // Placeholder, replace if joining Tester table
                }
                // If you joined Tester table and selected a name column:
                // feedback.setTesterIdentifier(rs.getString("TesterName"));

                feedback.setGameId(rs.getInt("Game_ID"));
                feedback.setFeedbackText(rs.getString("FeedBackText"));
                feedback.setAttachments(rs.getBytes("Attachments")); // Be cautious with BLOB size
                feedback.setReviewDate(rs.getDate("ReviewDate"));
                feedback.setFeedbackType(rs.getString("FeedbackType"));
                feedback.setStatus(rs.getString("Status"));
                feedback.setGameTitle(rs.getString("GameTitle")); // From the JOIN

                feedbackList.add(feedback);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Use proper logging
        }
        return feedbackList;
    }

    public String getDeveloperName(int gameDeveloperId) {
        String developerName = "Developer (ID: " + gameDeveloperId + ")"; // Default
        String sql = "SELECT Name FROM GameDeveloper WHERE GameDeveloperID = ?";
        try (Connection conn = DBConnectionManager.getConnection(); // Use your DB util
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, gameDeveloperId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String nameFromDb = rs.getString("Name");
                if (nameFromDb != null && !nameFromDb.trim().isEmpty()) {
                    developerName = nameFromDb;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Use proper logging
        }
        return developerName;
    }

}