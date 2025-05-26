package com.pregame.gametesting.controller;

import com.pregame.gametesting.dao.FeedbackDAO;
import com.pregame.gametesting.dao.GameDAO;
import com.pregame.gametesting.model.Feedback;
import com.pregame.gametesting.model.Game;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

@WebServlet("/feedback/submit")
public class FeedbackSubmitServlet extends HttpServlet {

    private GameDAO gameDAO;

    public FeedbackSubmitServlet() {
        this.gameDAO = new GameDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }

        // Get game ID from request
        String gameIdStr = request.getParameter("gameId");
        if (gameIdStr == null || gameIdStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/games/browse");
            return;
        }

        try {
            int gameId = Integer.parseInt(gameIdStr);

            // Fetch game information
            Game game = gameDAO.getGameById(gameId);
            if (game != null) {
                request.setAttribute("game", game);
            }

            // Set game ID for the form
            request.setAttribute("gameId", gameId);

            // Forward to the feedback form
            request.getRequestDispatcher("/WEB-INF/jsp/feedback/feedback_form.jsp")
                   .forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/games/browse");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error loading game information: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/feedback/feedback_form.jsp")
                   .forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }

        // Get parameters from form
        String gameIdStr = request.getParameter("gameId");
        String ratingStr = request.getParameter("rating");
        String feedbackText = request.getParameter("feedbackText");

        // Validate parameters
        if (gameIdStr == null || ratingStr == null || feedbackText == null ||
                gameIdStr.isEmpty() || ratingStr.isEmpty() || feedbackText.isEmpty()) {
            request.setAttribute("error", "All fields are required");
            request.getRequestDispatcher("/WEB-INF/jsp/feedback/feedback_form.jsp")
                    .forward(request, response);
            return;
        }

        try {
            int gameId = Integer.parseInt(gameIdStr);
            int rating = Integer.parseInt(ratingStr);

            // Create feedback object
            Feedback feedback = new Feedback();
            feedback.setGameId(gameId);
            feedback.setGamerId(userId);  // Assuming userId is the gamer's ID
            feedback.setRating(rating);
            feedback.setFeedbackText(feedbackText);
            feedback.setFeedbackDate(new Timestamp(new Date().getTime()));

            // Save feedback to database
            FeedbackDAO.insertFeedback(feedback);

            // Set success message and redirect to game page or feedback confirmation
            request.setAttribute("successMessage", "Your feedback has been submitted successfully!");
            response.sendRedirect(request.getContextPath() + "/games/browse");

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid game ID or rating");
            request.getRequestDispatcher("/WEB-INF/jsp/feedback/feedback_form.jsp")
                    .forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/feedback/feedback_form.jsp")
                    .forward(request, response);
        }
    }
}

