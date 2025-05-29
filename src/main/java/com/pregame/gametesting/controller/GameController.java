package com.pregame.gametesting.controller;

import com.pregame.gametesting.model.Game;
import com.pregame.gametesting.dao.GameDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/games/*")
public class GameController extends HttpServlet {

    private GameDAO gamedao;

    public GameController() {
        this.gamedao = new GameDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

      //  Cookie cookie = new Cookie("gameId", Game.getGameId());
        String pathInfo = request.getPathInfo();
        String action = pathInfo == null ? "browse" : pathInfo.substring(1);

        switch (action) {
            case "browse":
                showBrowsePage(request, response);
                break;
            case "details":
                showGameDetails(request, response);
                break;
            case "test":
                showTestGamePage(request, response);
                break;
            case "download":
                downloadGame(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/games/browse");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String action = pathInfo == null ? "browse" : pathInfo.substring(1);

        switch (action) {
            case "download":
                handleGameDownload(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request");
                break;
        }
    }

    private void showBrowsePage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get filter parameters
            String typeFilter = request.getParameter("type");
            String esrbFilter = request.getParameter("esrbRating");

            List<Game> games;

            // Apply filters if they exist
            if ((typeFilter != null && !typeFilter.isEmpty()) ||
                (esrbFilter != null && !esrbFilter.isEmpty())) {
                games = gamedao.getFilteredGames(typeFilter, esrbFilter);
            } else {
                // Get all games if no filters
                games = gamedao.getAllGames();
            }

            request.setAttribute("games", games);

            // Set the selected filter values for form persistence
            request.setAttribute("selectedType", typeFilter);
            request.setAttribute("selectedEsrb", esrbFilter);

        } catch (Exception e) {
            // Log the error
            System.err.println("Error retrieving games: " + e.getMessage());
            e.printStackTrace();

            // Create an empty list if there's an error
            request.setAttribute("games", new ArrayList<Game>());
            request.setAttribute("error", "An error occurred while retrieving games. Please try again later.");
        }

        // Forward to the Browse_page.jsp
        request.getRequestDispatcher("/jsp/games/Browse_page.jsp").forward(request, response);
    }

    /**
     * Show the game details page for a specific game
     */
    private void showGameDetails(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get the game ID from the request
            String gameIdParam = request.getParameter("id");

            if (gameIdParam == null || gameIdParam.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/games/browse");
                return;
            }

            int gameId = Integer.parseInt(gameIdParam);
            Game game = gamedao.getGameById(gameId);

            if (game == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Game not found");
                return;
            }

            // Set the game in the request
            request.setAttribute("game", game);

            // Forward to the Game.jsp
            request.getRequestDispatcher("/jsp/games/Game.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/games/browse");
        } catch (Exception e) {
            System.err.println("Error retrieving game details: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving game details");
        }
    }

    /**
     * Show the game testing page when a user clicks "Test Game"
     */
    private void showTestGamePage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get the game ID from the request
            String gameIdParam = request.getParameter("id");

            if (gameIdParam == null || gameIdParam.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/games/browse");
                return;
            }

            int gameId = Integer.parseInt(gameIdParam);
            Game game = gamedao.getGameById(gameId);

            if (game == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Game not found");
                return;
            }

            // Set the game in the request
            request.setAttribute("game", game);

            // Check if user is logged in
            Object user = request.getSession().getAttribute("user");
            if (user == null) {
                // Redirect to login page with return URL
                response.sendRedirect(request.getContextPath() +
                    "/auth?action=login&returnUrl=" +
                    request.getContextPath() + "/games/test?id=" + gameId);
                return;
            }

            // Forward to the TestGame.jsp
            request.getRequestDispatcher("/jsp/games/Game.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/games/browse");
        } catch (Exception e) {
            System.err.println("Error retrieving game for testing: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving game for testing");
        }
    }

    /**
     * Handle downloading a game file
     */
    private void downloadGame(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get the game ID from the request
            String gameIdParam = request.getParameter("id");

            System.out.println("Download request received for game ID: " + gameIdParam);

            if (gameIdParam == null || gameIdParam.isEmpty()) {
                System.out.println("No game ID provided, redirecting to browse page");
                response.sendRedirect(request.getContextPath() + "/games/browse");
                return;
            }

            int gameId = Integer.parseInt(gameIdParam);
            Game game = gamedao.getGameById(gameId);

            if (game == null) {
                System.out.println("Game not found for ID: " + gameId);
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Game not found");
                return;
            }

            // Check if the game has a download URL
            String downloadUrl = game.getDownloadUrl();
            if (downloadUrl == null || downloadUrl.trim().isEmpty()) {
                System.out.println("No download URL available for game: " + game.getTitle());

                // Forward to download page with error message
                request.setAttribute("game", game);
                request.setAttribute("error", "No download available for this game");
                request.getRequestDispatcher("/jsp/games/download.jsp").forward(request, response);
                return;
            }

            // Log the download attempt
            System.out.println("User is downloading game: " + game.getTitle() + " (ID: " + game.getGameId() + ")");
            System.out.println("Download URL: " + downloadUrl);

            // Set the game in the request for the download page
            request.setAttribute("game", game);

            // Forward to the download page instead of directly redirecting
            request.getRequestDispatcher("/jsp/games/download.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            System.out.println("Invalid game ID format: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/games/browse");
        } catch (Exception e) {
            System.err.println("Error downloading game: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error downloading game");
        }
    }

    /**
     * Process the actual download request from the form submission
     */
    private void handleGameDownload(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get the game ID from the request
            String gameIdParam = request.getParameter("gameId");

            System.out.println("Processing download for game ID: " + gameIdParam);

            if (gameIdParam == null || gameIdParam.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/games/browse");
                return;
            }

            int gameId = Integer.parseInt(gameIdParam);
            Game game = gamedao.getGameById(gameId);

            if (game == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Game not found");
                return;
            }

            String downloadUrl = game.getDownloadUrl();
            if (downloadUrl == null || downloadUrl.trim().isEmpty()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "No download URL available for this game");
                return;
            }

            // Log the download
            System.out.println("Starting download for game: " + game.getTitle() + " (ID: " + game.getGameId() + ")");

            // Redirect to the actual download URL in the cloud
            response.sendRedirect(downloadUrl);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/games/browse");
        } catch (Exception e) {
            System.err.println("Error processing game download: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing download");
        }
    }
}
