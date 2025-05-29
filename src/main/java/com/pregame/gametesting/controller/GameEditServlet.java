package com.pregame.gametesting.controller;

import com.pregame.gametesting.dao.GameDAO;
import com.pregame.gametesting.model.Game;
import com.pregame.gametesting.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/games/edit")
public class GameEditServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(GameEditServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the game ID from the request parameters
        String gameIdStr = request.getParameter("id");

        if (gameIdStr == null || gameIdStr.isEmpty()) {
            // If no game ID is provided, redirect to my games page
            logger.warning("No game ID provided for edit");
            response.sendRedirect(request.getContextPath() + "/developer/my-games");
            return;
        }

        // Check if user is logged in
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            // User not logged in, redirect to login
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            int gameId = Integer.parseInt(gameIdStr);

            // Get the game from the database
            GameDAO gameDAO = new GameDAO();
            Game game = gameDAO.getGameById(gameId);

            if (game == null) {
                // If the game doesn't exist, redirect to my games
                logger.warning("No game found with ID: " + gameId);
                response.sendRedirect(request.getContextPath() + "/developer/my-games");
                return;
            }

            // Check if the logged-in user is the owner of the game
            if (game.getDeveloper().getId() != currentUser.getId()) {
                // Not the owner, redirect to my games
                logger.warning("User " + currentUser.getId() + " attempted to edit game " + gameId + " owned by user " + game.getDeveloper().getId());
                response.sendRedirect(request.getContextPath() + "/developer/my-games");
                return;
            }

            // Set the game as an attribute to be accessed in the JSP
            request.setAttribute("game", game);

            // Forward to the edit game page
            request.getRequestDispatcher("/jsp/games/edit-game.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Invalid game ID format: {0}", gameIdStr);
            response.sendRedirect(request.getContextPath() + "/developer/my-games");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error retrieving game for editing", e);
            request.setAttribute("error", "An error occurred while retrieving the game.");
            response.sendRedirect(request.getContextPath() + "/developer/my-games");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the game ID and other form data
        String gameIdStr = request.getParameter("gameId");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String gameType = request.getParameter("type");
        String version = request.getParameter("version");
        String esrbRating = request.getParameter("esrbRating");

        // Check if user is logged in
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            int gameId = Integer.parseInt(gameIdStr);

            // Get the existing game
            GameDAO gameDAO = new GameDAO();
            Game game = gameDAO.getGameById(gameId);

            if (game == null) {
                logger.warning("No game found with ID: " + gameId);
                response.sendRedirect(request.getContextPath() + "/developer/my-games");
                return;
            }

            // Check if the logged-in user is the owner of the game
            if (game.getDeveloper().getId() != currentUser.getId()) {
                logger.warning("User " + currentUser.getId() + " attempted to update game " + gameId + " owned by user " + game.getDeveloper().getId());
                response.sendRedirect(request.getContextPath() + "/developer/my-games");
                return;
            }

            // Update game properties
            game.setTitle(title);
            game.setDescription(description);
            game.setType(gameType);
            game.setVersion(version);
            game.setEsrbRating(esrbRating);

            // Save the updated game
            gameDAO.updateGame(game);
            logger.info("Game updated successfully: " + gameId);

            // Set success message
            request.getSession().setAttribute("message", "Game updated successfully!");

            // Redirect to view the updated game instead of my-games
            response.sendRedirect(request.getContextPath() + "/games/view?id=" + gameId);

        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Invalid game ID format: {0}", gameIdStr);
            response.sendRedirect(request.getContextPath() + "/developer/my-games");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating game", e);
            request.getSession().setAttribute("error", "An error occurred while updating the game.");
            response.sendRedirect(request.getContextPath() + "/developer/my-games");
        }
    }
}
