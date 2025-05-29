package com.pregame.gametesting.dao;

import com.pregame.gametesting.model.Game;
import com.pregame.gametesting.model.GameDeveloper;
import com.pregame.gametesting.util.DBConnectionManager; // Assuming DBConnectionManager is in this package

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
// import java.math.BigDecimal; // Not used directly here, but Game model might use it

// Static import for getConnection
import static com.pregame.gametesting.util.DBConnectionManager.getConnection;

public class GameDAO {

    // Maps a ResultSet row to a Game object (basic fields)
    private Game mapResultSetToGame(ResultSet rs) throws SQLException {
        Game game = new Game();
        game.setGameId(rs.getInt("GameID"));
        game.setTitle(rs.getString("Title"));
        game.setReleaseDate(rs.getDate("ReleaseDate"));
        game.setEsrbRating(rs.getString("ESRB"));
        game.setType(rs.getString("Type"));
        game.setSize(rs.getBigDecimal("Size"));
        game.setVersion(rs.getString("Version"));
        game.setGameDeveloperId(rs.getInt("GameDeveloperID")); // Sets the FK ID
        game.setDescription(rs.getString("DescriptionofGame"));

        // Check for downloadUrl column (it might not exist in older database schemas)
        try {
            game.setDownloadUrl(rs.getString("DownloadURL"));
        } catch (SQLException e) {
            // Column doesn't exist, set a default or leave as null
            game.setDownloadUrl(null);
        }

        return game;
    }

    public int insertGame(Game game) throws SQLException {
        String sql = "INSERT INTO Game (Title, ReleaseDate, ESRB, Type, Size, Version, GameDeveloperID, DescriptionofGame, DownloadURL) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;
        int gameId = -1;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, game.getTitle());
            if (game.getReleaseDate() != null) {
                stmt.setDate(2, new java.sql.Date(game.getReleaseDate().getTime()));
            } else {
                stmt.setNull(2, Types.DATE);
            }
            stmt.setString(3, game.getEsrbRating());
            stmt.setString(4, game.getType());
            stmt.setBigDecimal(5, game.getSize());
            stmt.setString(6, game.getVersion());
            stmt.setInt(7, game.getGameDeveloperId());
            stmt.setString(8, game.getDescription());
            stmt.setString(9, game.getDownloadUrl()); // Set downloadUrl

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    gameId = generatedKeys.getInt(1);
                    game.setGameId(gameId); // Set the generated ID back to the game object
                }
            }
        } finally {
            closeResources(conn, stmt, generatedKeys);
        }
        return gameId;
    }
    public static Game getGameById(int gameId) throws SQLException {
        // Select all game fields and developer's individual name and company name
        String sql = "SELECT g.*, " + // g.* fetches all columns from Game table
                "d.Name AS DeveloperIndividualName, d.CompanyName AS DeveloperCompanyName " +
                "FROM Game g " +
                "LEFT JOIN GameDeveloper d ON g.GameDeveloperID = d.GameDeveloperID " +
                "WHERE g.GameID = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Game game = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, gameId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                // Replace "this.mapResultSetToGame" with a new instance of GameDAO
                GameDAO dao = new GameDAO();
                game = dao.mapResultSetToGame(rs); // Maps base Game object

                // Populate the nested GameDeveloper object
                if (rs.getObject("GameDeveloperID") != null) {
                    GameDeveloper developer = new GameDeveloper();
                    developer.setGameDeveloperid(game.getGameDeveloperId());
                    developer.setName(rs.getString("DeveloperIndividualName"));
                    game.setDeveloper(developer);
                }
            }
        } finally {
            // Change closeResources to static method call or create an instance
            closeResourcesStatic(conn, stmt, rs);
        }
        return game;
    }

    // Add this static helper method
    private static void closeResourcesStatic(Connection conn, PreparedStatement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            System.err.println("Error closing ResultSet: " + e.getMessage());
        }
        try {
            if (stmt != null) stmt.close();
        } catch (SQLException e) {
            System.err.println("Error closing Statement: " + e.getMessage());
        }
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.err.println("Error closing Connection: " + e.getMessage());
        }
    }

    public List<Game> getFilteredGames(String type, String esrbRating) throws SQLException {
        StringBuilder sqlBuilder = new StringBuilder(
                "SELECT g.*, " + // g.* fetches all columns from Game table
                        "d.Name AS DeveloperIndividualName, d.CompanyName AS DeveloperCompanyName " +
                        "FROM Game g " +
                        "LEFT JOIN GameDeveloper d ON g.GameDeveloperID = d.GameDeveloperID " +
                        "WHERE 1=1" // Start for easy AND appending
        );

        List<Object> params = new ArrayList<>();

        if (type != null && !type.isEmpty()) {
            sqlBuilder.append(" AND g.Type = ?");
            params.add(type);
        }

        if (esrbRating != null && !esrbRating.isEmpty()) {
            sqlBuilder.append(" AND g.ESRB = ?");
            params.add(esrbRating);
        }

        sqlBuilder.append(" ORDER BY g.Title");
        String sql = sqlBuilder.toString();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Game> games = new ArrayList<>();

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            rs = stmt.executeQuery();

            while (rs.next()) {
                Game game = mapResultSetToGame(rs); // Maps base Game object

                // Populate the nested GameDeveloper object
                if (rs.getObject("GameDeveloperID") != null) { // Check if developer info is present
                    GameDeveloper developer = new GameDeveloper();
                    developer.setGameDeveloperid(game.getGameDeveloperId()); // ID from game object
                    developer.setName(rs.getString("DeveloperIndividualName"));

                    // Assuming GameDeveloper model has setCompanyName:
                    // developer.setCompanyName(rs.getString("DeveloperCompanyName"));

                    game.setDeveloper(developer);
                }
                games.add(game);
            }
        } finally {
            closeResources(conn, stmt, rs);
        }
        return games;
    }

    public List<Game> getAllGames() throws SQLException {
        // Simply call getFilteredGames with null filters
        return getFilteredGames(null, null);
    }

    /**
     * Retrieves all games uploaded by a specific developer.
     * @param developerId The ID of the game developer.
     * @return List of Game objects associated with the developer.
     * @throws SQLException if a database access error occurs.
     */
    public List<Game> getGamesByDeveloperId(int developerId) throws SQLException {
        // Select all game fields (g.*) and specific developer fields (name, company name)
        String sql = "SELECT g.*, " +
                "gd.Name AS DeveloperIndividualName, gd.CompanyName AS DeveloperCompanyName " +
                "FROM Game g " +
                "LEFT JOIN GameDeveloper gd ON g.GameDeveloperID = gd.GameDeveloperID " +
                "WHERE g.GameDeveloperID = ?"; // Filter by the specific developer ID

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Game> games = new ArrayList<>();

        System.out.println("Executing SQL for getGamesByDeveloperId with Developer ID: " + developerId);
        // System.out.println("SQL: " + sql); // SQL is now well-defined

        try {
            conn = getConnection();
            if (conn == null) {
                System.err.println("Error: Database connection is null in getGamesByDeveloperId!");
                return games; // Return empty list if no connection
            }

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, developerId); // Set the developerId parameter for the WHERE clause
            rs = stmt.executeQuery();

            while (rs.next()) {
                Game game = mapResultSetToGame(rs); // mapResultSetToGame sets game.gameDeveloperId

                // Create and populate the GameDeveloper object for this game
                // Since we queried for a specific developerId, the GameDeveloper object will correspond to that.
                GameDeveloper gameSpecificDeveloper = new GameDeveloper();
                gameSpecificDeveloper.setGameDeveloperid(game.getGameDeveloperId()); // This ID is developerId

                String individualName = rs.getString("DeveloperIndividualName");
                String companyName = rs.getString("DeveloperCompanyName");

                gameSpecificDeveloper.setName(individualName); // Set individual's name

                // If your GameDeveloper model is designed to hold companyName separately:
                // (Requires GameDeveloper class to have setCompanyName method)
                // gameSpecificDeveloper.setCompanyName(companyName);
                // Or if GameDeveloper.name field is intended for Company Name:
                // gameSpecificDeveloper.setName(companyName);

                game.setDeveloper(gameSpecificDeveloper);
                games.add(game);
            }
        } finally {
            closeResources(conn, stmt, rs);
        }
        return games;
    }


    /**
     * Updates a game in the database
     *
     * @param game The Game object with updated values
     * @return true if update was successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean updateGame(Game game) throws SQLException {
        String sql = "UPDATE Game SET Title = ?, ESRB = ?, Type = ?, Version = ?, DescriptionofGame = ? WHERE GameID = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, game.getTitle());
            stmt.setString(2, game.getEsrbRating());
            stmt.setString(3, game.getType());
            stmt.setString(4, game.getVersion());
            stmt.setString(5, game.getDescription());
            stmt.setInt(6, game.getGameId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating game: " + e.getMessage());
            throw e;
        }
    }

    public boolean deleteGame(int gameId) throws SQLException {
        String sql = "DELETE FROM Game WHERE GameID=?";
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, gameId);
            success = stmt.executeUpdate() > 0;
        } finally {
            closeResources(conn, stmt, null);
        }
        return success;
    }

    /**
     * Gets the GameDeveloperID for a given User ID
     * This resolves the foreign key constraint by finding the corresponding developer ID
     *
     * @param userId The user ID to look up
     * @return The GameDeveloperID, or 0 if not found
     * @throws SQLException If a database error occurs
     */
    public int getGameDeveloperIdForUserId(int userId) throws SQLException {
        int gameDeveloperId = 0;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT gd.GameDeveloperID FROM gamedeveloper gd " +
                 "JOIN user u ON gd.UserID = u.UserID " +
                 "WHERE u.UserID = ?")) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    gameDeveloperId = rs.getInt("GameDeveloperID");
                } else {
                    // Try direct lookup if the user ID is the same as developer ID
                    try (PreparedStatement directStmt = conn.prepareStatement(
                        "SELECT GameDeveloperID FROM gamedeveloper WHERE GameDeveloperID = ?")) {

                        directStmt.setInt(1, userId);
                        try (ResultSet directRs = directStmt.executeQuery()) {
                            if (directRs.next()) {
                                gameDeveloperId = directRs.getInt("GameDeveloperID");
                            }
                        }
                    }
                }
            }
        }

        return gameDeveloperId;
    }

    // Helper method for closing resources (consolidated)
    private void closeResources(Connection conn, PreparedStatement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            System.err.println("Error closing ResultSet: " + e.getMessage());
        }
        try {
            if (stmt != null) stmt.close();
        } catch (SQLException e) {
            System.err.println("Error closing Statement: " + e.getMessage());
        }
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.err.println("Error closing Connection: " + e.getMessage());
        }
    }
    // Removed individual close methods as closeResources handles all.
//

}
