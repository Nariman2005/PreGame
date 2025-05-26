package com.pregame.gametesting.model;

/**
 * Gamer user implementation for the game testing system.
 */
public class Gamer extends User {

    private int gamerId;// Specific ID for gamers
    private int level;

    public Gamer() {
        super();
    }

    public Gamer(String name, String password, String email) {
        super(name, password, email);
    }

    @Override
    public String getUserType() {
        return User.TYPE_GAMER;
    }

    public int getGamerId() {
        return gamerId;
    }

    public void setGamerId(int gamerId) {
        this.gamerId = gamerId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}