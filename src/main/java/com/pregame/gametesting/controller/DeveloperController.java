package com.pregame.gametesting.controller;

import com.pregame.gametesting.dao.GameDAO;
import com.pregame.gametesting.model.Game;
import com.pregame.gametesting.dao.TesterFeedbackDAO;
import com.pregame.gametesting.model.Review; // <--- IMPORT THIS
import com.pregame.gametesting.model.User;
import com.pregame.gametesting.model.GameDeveloper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
    // Keep if used elsewhere

@WebServlet(urlPatterns ={"/developer/*","/developer/reviews-feedbacks", "/upload-game"})
public class DeveloperController extends HttpServlet {

    private GameDAO gameDAO;
    private TesterFeedbackDAO testerFeedbackDAO; // Already declared

    public DeveloperController() {
        this.gameDAO = new GameDAO();
        this.testerFeedbackDAO = new TesterFeedbackDAO(); // Already instantiated
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getPathInfo();
        String servletPath = request.getServletPath();

        System.out.println("DeveloperController: ServletPath=" + servletPath + ", PathInfo=" + action);

        // Normalize action for direct mapping
        if (servletPath.equals("/developer/reviews-feedbacks") && (action == null || action.equals("/"))) {
            action = "/reviews-feedbacks";
        } else if (servletPath.equals("/upload-game")) {
            // Handle the upload-game request
            showUploadGameForm(request, response);
            return;
        }

        if (action == null || action.equals("/")) {
            showDeveloperProfile(request, response);
        } else if (action.equals("/games")) {
            showDeveloperGames(request, response);
        } else if (action.equals("/reviews-feedbacks")) {
            showDeveloperFeedback(request, response);// <--- CALL NEW METHOD
        } else {
            System.out.println("DeveloperController: Unknown action '" + action + "'. Sending 404.");
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "The requested developer resource was not found.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }

    private void showDeveloperProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/developer/games");
    }
    private void showDeveloperGames(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/auth?action=login");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (!User.TYPE_DEVELOPER.equals(user.getUserType())) { // Use .equals for string comparison
            System.out.println("User is not a developer. User type: " + user.getUserType());
            request.setAttribute("errorMessage", "Only developers can access this page.");
            // Consider forwarding to an error page or dashboard with message
            request.getRequestDispatcher("/jsp/Profiles/developer_profile.jsp").forward(request, response); // Or some other appropriate page
            return;
        }

        int developerId = getDeveloperIdFromSession(user);
        if (developerId == -1) { // Indicates an issue getting ID
            request.setAttribute("errorMessage", "Could not determine Developer ID.");
            request.getRequestDispatcher("/jsp/developer/dashboard.jsp").forward(request, response);
            return;
        }


        System.out.println("Attempting to retrieve games for developer ID: " + developerId);
        try {
            List<Game> developerGames = gameDAO.getGamesByDeveloperId(developerId);
            System.out.println("Query executed. Retrieved " +
                    (developerGames != null ? developerGames.size() : "null") + " games");

            request.setAttribute("games", developerGames);
            request.getRequestDispatcher("/jsp/developer/my_games.jsp").forward(request, response);
        } catch (SQLException e) {
            System.err.println("Error retrieving developer games: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while retrieving your games: " + e.getMessage());
            request.getRequestDispatcher("/jsp/error.jsp").forward(request, response); // Example error page
        }
    }

    // New method to get developer ID from session user
    private int getDeveloperIdFromSession(User user) {
        int developerId = -1; // Default to an invalid ID
        if (user instanceof GameDeveloper) {
            GameDeveloper developer = (GameDeveloper) user;
            developerId = developer.getGameDeveloperid();
            System.out.println("User is GameDeveloper. GameDeveloperID: " + developerId);
            if (developerId == 0 && user.getId() != 0) { // If GameDeveloperID is 0, fallback to User's ID if it's valid
                System.out.println("GameDeveloperID is 0, falling back to User ID: " + user.getId());
                developerId = user.getId();
            }
        } else {
            // If user is not directly a GameDeveloper instance but type is developer,
            // user.getId() might be the GameDeveloperID.
            // This depends on how your User and GameDeveloper objects are structured and populated.
            developerId = user.getId();
            System.out.println("User is not instance of GameDeveloper, using User ID as Developer ID: " + developerId);
        }

        if (developerId == 0) { // If still 0, it's likely an issue
            System.err.println("Warning: Developer ID resolved to 0. This might be incorrect.");
            return -1; // Indicate an issue
        }
        return developerId;
    }



    // NEW METHOD to show developer feedback
    private void showDeveloperFeedback(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/auth?action=login");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (!User.TYPE_DEVELOPER.equals(user.getUserType())) { // Use .equals for string comparison
            request.setAttribute("errorMessage", "Access denied. Only developers can view feedback.");
            System.out.println("User is not a developer (type: " + user.getUserType() + "). Cannot view feedback.");
            // Forward to a relevant page with error message
            request.getRequestDispatcher("/jsp/Profiles/developer_profile.jsp").forward(request, response); // Or an error page
            return;
        }

        int developerId = getDeveloperIdFromSession(user);
        if (developerId == -1) { // Indicates an issue getting ID
            request.setAttribute("errorMessage", "Could not determine Developer ID to fetch feedback.");
            request.getRequestDispatcher("/jsp/Profiles/developer_profile.jsp").forward(request, response);
            return;
        }

        System.out.println("Fetching feedback for developer ID: " + developerId);

        try {
            // Fetch feedback list for the developer
            List<Review> feedbackList = testerFeedbackDAO.getFeedbackForDeveloper(developerId);

            // Fetch developer name
            String developerName = testerFeedbackDAO.getDeveloperName(developerId);

            // Set attributes for the JSP
            request.setAttribute("feedbackList", feedbackList);
            request.setAttribute("developerName", developerName);
            request.setAttribute("requestedDeveloperId", developerId);

            System.out.println("Fetched " + (feedbackList != null ? feedbackList.size() : "null") +
                    " feedback items for developer: " + developerName + " (ID: " + developerId + ")");

            // Forward to the JSP (ensure this path is correct)
            request.getRequestDispatcher("/jsp/developer/developer_reviews_feedbacks.jsp").forward(request, response);

        } catch (Exception e) { // Catch a broader exception for safety
            System.err.println("Error retrieving developer feedback for ID " + developerId + ": " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while retrieving feedback: " + e.getMessage());
        }
    }

    // Add a new method to handle the upload game form request
    private void showUploadGameForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if user is logged in and is a developer
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/auth?action=login");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (!User.TYPE_DEVELOPER.equals(user.getUserType())) {
            request.setAttribute("errorMessage", "Access denied. Only developers can upload games.");
            request.getRequestDispatcher("/jsp/Profiles/developer_profile.jsp").forward(request, response);
            return;
        }

        // Forward to the upload game form
        request.getRequestDispatcher("/jsp/developer/upload-game.jsp").forward(request, response);
    }
}
