package com.pregame.gametesting.controller;

import com.pregame.gametesting.dao.GameDAO;
import com.pregame.gametesting.model.Game;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/games/view")
public class GameDisplayServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(GameDisplayServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the game ID from the request parameters
        String gameIdStr = request.getParameter("id");

        if (gameIdStr == null || gameIdStr.isEmpty()) {
            // If no game ID is provided, redirect to the browse page
            logger.warning("No game ID provided for view");
            response.sendRedirect(request.getContextPath() + "/games/browse");
            return;
        }

        try {
            int gameId = Integer.parseInt(gameIdStr);

            // Get the game from the database
            GameDAO gameDAO = new GameDAO();
            Game game = gameDAO.getGameById(gameId);

            if (game == null) {
                // If the game doesn't exist, redirect to the browse page
                logger.warning("No game found with ID: " + gameId);
                response.sendRedirect(request.getContextPath() + "/games/browse");
                return;
            }

            // Set the game as an attribute to be accessed in the JSP
            request.setAttribute("game", game);

            // Forward to the game view page
            request.getRequestDispatcher("/jsp/games/Game.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Invalid game ID format: {0}", gameIdStr);
            response.sendRedirect(request.getContextPath() + "/games/browse");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error retrieving game for viewing", e);
            request.setAttribute("error", "An error occurred while retrieving the game.");
            response.sendRedirect(request.getContextPath() + "/games/browse");
        }
    }
}
