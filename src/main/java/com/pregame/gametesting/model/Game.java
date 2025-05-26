package com.pregame.gametesting.model;

import java.math.BigDecimal; // For DECIMAL type from DB
import java.util.Date;
import java.util.Objects;

public class Game implements java.io.Serializable {
    private int gameId;
    private String title;
    private Date releaseDate;
    private String esrbRating; // Schema has 'ESRB'
    private String type;
    private BigDecimal size;   // Schema has DECIMAL(5,2), use BigDecimal for precision
    private String version;
    private String description; // Schema has 'DescriptionofGame'
    private int gameDeveloperId; // Foreign Key
    private String downloadUrl; // URL to the game file in cloud storage

    // Optional full object, to be populated by a service layer
    private GameDeveloper developer;

    public Game() {
    }

    // Constructor with essential fields
    public Game(String title, Date releaseDate, String esrbRating, String type,
                BigDecimal size, String version, String description, int gameDeveloperId) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.esrbRating = esrbRating;
        this.type = type;
        this.size = size;
        this.version = version;
        this.description = description;
        this.gameDeveloperId = gameDeveloperId;
    }

    // Constructor with download URL
    public Game(String title, Date releaseDate, String esrbRating, String type,
                BigDecimal size, String version, String description, int gameDeveloperId,
                String downloadUrl) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.esrbRating = esrbRating;
        this.type = type;
        this.size = size;
        this.version = version;
        this.description = description;
        this.gameDeveloperId = gameDeveloperId;
        this.downloadUrl = downloadUrl;
    }

    // --- Getters and Setters ---
    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getEsrbRating() {
        return esrbRating;
    }

    public void setEsrbRating(String esrbRating) {
        this.esrbRating = esrbRating;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getSize() { // Use BigDecimal
        return size;
    }

    public void setSize(BigDecimal size) { // Use BigDecimal
        this.size = size;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getGameDeveloperId() {
        return gameDeveloperId;
    }

    public void setGameDeveloperId(int gameDeveloperId) {
        this.gameDeveloperId = gameDeveloperId;
        // If the full developer object exists and its ID doesn't match, nullify it
        // to signal that it might be stale and needs reloading by the service.
        if (this.developer != null && this.developer.getGameDeveloperid() != gameDeveloperId) {
            this.developer = null;
        }
    }

    public GameDeveloper getDeveloper() {
        return developer;
    }

    public void setDeveloper(GameDeveloper developer) {
        this.developer = developer;
        // If a developer object is set, ensure its ID matches our gameDeveloperId
        if (developer != null) {
            this.gameDeveloperId = developer.getGameDeveloperid();
        }
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameId=" + gameId +
                ", title='" + title + '\'' +
                ", version='" + version + '\'' +
                ", gameDeveloperId=" + gameDeveloperId +
                '}';
    }
}

