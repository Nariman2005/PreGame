package com.pregame.gametesting.controller;

import com.pregame.gametesting.dao.GameDAO;
import com.pregame.gametesting.model.Game;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/feedback/form", "/feedback"})
public class FeedbackFormServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        String userType = (String) session.getAttribute("userType");

        // Check if user is logged in
        if (userId == null || userType == null) {
            // User not logged in, redirect to login page
            response.sendRedirect(request.getContextPath() + "/auth/login?redirect=feedback");
            return;
        }

        // Get game ID from request
        String gameIdParam = request.getParameter("gameId");

        if (gameIdParam != null && !gameIdParam.isEmpty()) {
            try {
                int gameId = Integer.parseInt(gameIdParam);

                // Get game details to display in the feedback form
                Game game = GameDAO.getGameById(gameId);
                if (game != null) {
                    request.setAttribute("game", game);
                }

                // Save gameId in request
                request.setAttribute("gameId", gameId);

                // Forward to feedback form
                request.getRequestDispatcher("/WEB-INF/jsp/feedback/feedback_form.jsp")
                        .forward(request, response);
                return;

            } catch (NumberFormatException e) {
                // Invalid game ID
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid game ID");
                return;
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Error retrieving game information");
                return;
            }
        }

        // No game ID provided
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Game ID is required");
    }
}