package com.pregame.gametesting.dao;

import com.pregame.gametesting.model.Review;
import static com.pregame.gametesting.util.DBConnectionManager.getConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object for Reviews
 */
public class ReviewDAO {
    private static final Logger logger = Logger.getLogger(ReviewDAO.class.getName());

    /**
     * Get all reviews/feedback for games developed by the specified developer
     *
     * @param developerId ID of the developer
     * @return List of Review objects with tester names attached
     */
    public List<Review> getReviewsByDeveloperId(int developerId) {
        List<Review> reviews = new ArrayList<>();

        // SQL query that joins Review table with Game table and Tester table
        // This matches your specific database schema with Tester_ID and Game_ID fields
        String sql = "SELECT r.*, g.Title AS game_title, " +
                     "t.Name AS tester_name " +
                     "FROM Review r " +
                     "JOIN Game g ON r.Game_ID = g.GameID " +
                     "LEFT JOIN Tester t ON r.Tester_ID = t.TesterID " +
                     "WHERE g.GameDeveloperID = ?";

        logger.info("Executing SQL: " + sql + " with developer ID: " + developerId);

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, developerId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Review review = mapResultSetToReview(rs);
                    review.setGameTitle(rs.getString("game_title"));

                    // Use the tester's actual name from the database
                    String testerName = rs.getString("tester_name");
                    logger.info("Found tester name: " + testerName + " for review ID: " + review.getReviewId());

                    if (testerName != null && !rs.wasNull()) {
                        review.setTesterIdentifier(testerName);
                    } else {
                        // Fallback if name is null
                        review.setTesterIdentifier("Anonymous Tester");
                    }

                    reviews.add(review);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching reviews for developer: " + developerId, e);
        }

        return reviews;
    }

    /**
     * Maps a ResultSet row to a Review object based on your database schema
     */
    private Review mapResultSetToReview(ResultSet rs) throws SQLException {
        Review review = new Review();

        review.setReviewId(rs.getInt("ReviewID"));

        // Handle potentially null Tester_ID (using the correct column name from your schema)
        Object testerId = rs.getObject("Tester_ID");
        if (testerId != null && !rs.wasNull()) {
            review.setTesterId((Integer) testerId);
        }

        review.setGameId(rs.getInt("Game_ID"));
        review.setFeedbackText(rs.getString("FeedBackText"));
        review.setReviewDate(rs.getDate("ReviewDate"));
        review.setFeedbackType(rs.getString("FeedbackType"));
        review.setStatus(rs.getString("Status"));

        // Handle binary attachments if they exist
        byte[] attachments = rs.getBytes("Attachments");
        if (attachments != null && !rs.wasNull()) {
            review.setAttachments(attachments);
        }

        return review;
    }

    /**
     * Get a specific review by ID
     */
    public Review getReviewById(int reviewId) {
        String sql = "SELECT r.*, g.Title AS game_title, " +
                     "t.Name AS tester_name " +
                     "FROM Review r " +
                     "JOIN Game g ON r.Game_ID = g.GameID " +
                     "LEFT JOIN Tester t ON r.Tester_ID = t.TesterID " +
                     "WHERE r.ReviewID = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reviewId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Review review = mapResultSetToReview(rs);
                    review.setGameTitle(rs.getString("game_title"));

                    // Use the tester's actual name from the database
                    String testerName = rs.getString("tester_name");

                    if (testerName != null && !rs.wasNull()) {
                        review.setTesterIdentifier(testerName);
                    } else {
                        // Fallback if name is null
                        review.setTesterIdentifier("Anonymous Tester");
                    }

                    return review;
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching review with ID: " + reviewId, e);
        }

        return null;
    }

    /**
     * Get all reviews for a specific game
     */
    public List<Review> getReviewsByGameId(int gameId) {
        List<Review> reviews = new ArrayList<>();

        // SQL query that joins with Tester table to get names
        String sql = "SELECT r.*, " +
                     "t.Name AS tester_name " +
                     "FROM Review r " +
                     "LEFT JOIN Tester t ON r.Tester_ID = t.TesterID " +
                     "WHERE r.Game_ID = ? " +
                     "ORDER BY r.ReviewDate DESC";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, gameId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Review review = mapResultSetToReview(rs);

                    // Use the tester's actual name from the database
                    String testerName = rs.getString("tester_name");

                    if (testerName != null && !rs.wasNull()) {
                        review.setTesterIdentifier(testerName);
                    } else {
                        // Fallback if name is null
                        review.setTesterIdentifier("Anonymous Tester");
                    }

                    reviews.add(review);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching reviews for game: " + gameId, e);
        }

        return reviews;
    }
}
