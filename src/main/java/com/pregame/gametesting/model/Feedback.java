package com.pregame.gametesting.model;
// Or your package

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Feedback {
    private int feedbackId;
    private int gamerId; // Foreign Key to Gamer
    private int gameId; // Foreign Key to Game
    private String feedbackText;
    private byte[] attachments;
    private Date feedbackDate;
    private int rating;
    private String email; // Added email field

    // Optional full objects, populated by a service layer
    private Gamer gamer;
    private Game game;

    public Feedback() {
        this.feedbackDate = new Date(); // Default to current date
    }

    public Feedback(int gamerId, int gameId, String feedbackText, byte[] attachments, Date feedbackDate) {
        this.gamerId = gamerId;
        this.gameId = gameId;
        this.feedbackText = feedbackText;
        this.attachments = attachments;
        this.feedbackDate = (feedbackDate != null) ? feedbackDate : new Date();
    }

    // --- Getters and Setters for IDs ---
    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public int getGamerId() {
        return gamerId;
    }

    public void setGamerId(int gamerId) {
        this.gamerId = gamerId;
        // If full Gamer object exists and its ID doesn't match, nullify it
        if (this.gamer != null && this.gamer.getGamerId() != gamerId) {
            this.gamer = null;
        }
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
        // If full Game object exists and its ID doesn't match, nullify it
        if (this.game != null && this.game.getGameId() != gameId) {
            this.game = null;
        }
    }

    // --- Getters and Setters for content ---
    public String getFeedbackText() {
        return feedbackText;
    }

    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    public byte[] getAttachments() {
        return attachments;
    }

    public void setAttachments(byte[] attachments) {
        this.attachments = attachments;
    }

    public Date getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(Date feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        this.rating = rating;
    }




    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // --- Getters and Setters for full objects (used by service layer) ---
    public Gamer getGamer() {
        return gamer;
    }

    public void setGamer(Gamer gamer) {
        this.gamer = gamer;
        if (this.gamer != null) {
            this.gamerId = this.gamer.getGamerId(); // Keep ID in sync
        }
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
        if (this.game != null) {
            this.gameId = this.game.getGameId(); // Keep ID in sync
        }
    }
}

