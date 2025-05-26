package com.pregame.gametesting.model; // Your package name

import java.sql.Date; // java.sql.Date for DATE type

public class Review { // Renamed for clarity, maps to your 'Review' table
    private int reviewId;
    private Integer testerId; // Can be null
    private int gameId;
    private String feedbackText;
    private byte[] attachments; // For BLOB. Consider storing path or separate retrieval
    private Date reviewDate;
    private String feedbackType;
    private String status;

    // To hold related information for display
    private String gameTitle; // From the Game table
    private String testerIdentifier; // Could be Tester ID or name if you join Tester table

    // Constructors
    public Review() {
    }

    // Getters and Setters
    public int getReviewId() { return reviewId; }
    public void setReviewId(int reviewId) { this.reviewId = reviewId; }

    public Integer getTesterId() { return testerId; }
    public void setTesterId(Integer testerId) { this.testerId = testerId; }

    public int getGameId() { return gameId; }
    public void setGameId(int gameId) { this.gameId = gameId; }

    public String getFeedbackText() { return feedbackText; }
    public void setFeedbackText(String feedbackText) { this.feedbackText = feedbackText; }

    public byte[] getAttachments() { return attachments; }
    public void setAttachments(byte[] attachments) { this.attachments = attachments; }

    public Date getReviewDate() { return reviewDate; }
    public void setReviewDate(Date reviewDate) { this.reviewDate = reviewDate; }

    public String getFeedbackType() { return feedbackType; }
    public void setFeedbackType(String feedbackType) { this.feedbackType = feedbackType; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getGameTitle() { return gameTitle; }
    public void setGameTitle(String gameTitle) { this.gameTitle = gameTitle; }

    public String getTesterIdentifier() { return testerIdentifier; }
    public void setTesterIdentifier(String testerIdentifier) { this.testerIdentifier = testerIdentifier; }

    // toString (optional)
    @Override
    public String toString() {
        return "ReviewFeedback [reviewId=" + reviewId + ", gameTitle=" + gameTitle + ", feedbackType=" + feedbackType + ", status=" + status + "]";
    }
}