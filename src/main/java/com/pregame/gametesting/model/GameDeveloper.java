package com.pregame.gametesting.model;

import java.util.ArrayList;
import java.util.List;

/**
 * GameDeveloper user implementation for the game testing system.
 */
public class GameDeveloper extends User {

    String companyName;
    private int GameDeveloperid;
    private List<Game> games = new ArrayList<>();

    public GameDeveloper() {
        super();
    }

    public GameDeveloper(String name, String password, String email) {
        super(name, password, email);
    }

    @Override
    public String getUserType() {
        return User.TYPE_DEVELOPER;
    }

    public int getGameDeveloperid() {
        return GameDeveloperid;
    }

    public void setGameDeveloperid(int gameDeveloperid) {
        this.GameDeveloperid = gameDeveloperid;
    }

    public List<Game> getGames() {
        return games;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public void addGame(Game game) {
        this.games.add(game);
    }
}