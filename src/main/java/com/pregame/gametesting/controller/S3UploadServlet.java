package com.pregame.gametesting.controller;

import com.pregame.gametesting.dao.GameDAO;
import com.pregame.gametesting.model.Game;
import com.pregame.gametesting.model.GameDeveloper;
import com.pregame.gametesting.model.User;
import com.pregame.gametesting.util.AWSS3Utility;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;

@WebServlet("/s3upload")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,     // 1 MB
        maxFileSize = 1024 * 1024 * 50,      // 50 MB
        maxRequestSize = 1024 * 1024 * 100    // 100 MB
)
public class S3UploadServlet extends HttpServlet {

    private GameDAO gameDAO;

    public S3UploadServlet() {
        this.gameDAO = new GameDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if user is logged in and is a developer
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/auth?action=login");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (!User.TYPE_DEVELOPER.equals(user.getUserType())) {
            request.setAttribute("errorMessage", "Access denied. Only developers can upload games.");
            request.getRequestDispatcher("/jsp/developer/upload-game.jsp").forward(request, response);
            return;
        }

        try {
            // Get form fields
            String gameTitle = escapeHtml(request.getParameter("gameTitle"));
            String gameDescription = escapeHtml(request.getParameter("gameDescription"));
            String gameGenre = escapeHtml(request.getParameter("gameGenre"));
            String gameVersion = escapeHtml(request.getParameter("gameVersion"));
            String esrbRating = escapeHtml(request.getParameter("esrbRating"));

            // Convert full ESRB rating names to their abbreviated forms
            esrbRating = convertToEsrbAbbreviation(esrbRating);

            // Get file part
            Part filePart = request.getPart("gameFile");
            String fileName = getSubmittedFileName(filePart);
            String contentType = filePart.getContentType();
            long fileSize = filePart.getSize();

            // Upload file to S3
            String downloadUrl = AWSS3Utility.uploadFile(
                    filePart.getInputStream(),
                    fileName,
                    contentType,
                    fileSize
            );

            // Create a new Game object
            Game game = new Game();
            game.setTitle(gameTitle);
            game.setDescription(gameDescription);
            game.setType(gameGenre); // Using genre as type
            game.setVersion(gameVersion);
            game.setEsrbRating(esrbRating); // Using the abbreviated ESRB rating

            // Set file size (converting bytes to MB)
            BigDecimal sizeInMB = new BigDecimal(fileSize).divide(new BigDecimal(1024 * 1024));
            game.setSize(sizeInMB);

            // Set release date to current date
            game.setReleaseDate(new Date());

            // Set download URL
            game.setDownloadUrl(downloadUrl);

            // Get correct developer ID from session (using the specific GameDeveloper ID)
            int developerId;
            if (user instanceof GameDeveloper) {
                GameDeveloper developer = (GameDeveloper) user;
                developerId = developer.getGameDeveloperid();
                System.out.println("Using GameDeveloperID: " + developerId);
            } else {
                // Fallback to regular ID if not a GameDeveloper instance
                developerId = user.getId();
                System.out.println("Warning: User is not instance of GameDeveloper, using User ID: " + developerId);
            }

            game.setGameDeveloperId(developerId);

            // Save game to database
            int gameId = gameDAO.insertGame(game);

            if (gameId > 0) {
                // Game was successfully added to database
                // Redirect to the My Games page with success parameter
                response.sendRedirect(request.getContextPath() + "/developer/games?upload=success&gameId=" + gameId);
            } else {
                // There was an issue with database insertion
                request.setAttribute("errorMessage", "Failed to save game in database.");
                request.getRequestDispatcher("/jsp/developer/upload-game.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/jsp/developer/upload-game.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "File upload failed: " + e.getMessage());
            request.getRequestDispatcher("/jsp/developer/upload-game.jsp").forward(request, response);
        }
    }

    // Helper method to extract file name from Part
    private String getSubmittedFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String item : items) {
            if (item.trim().startsWith("filename")) {
                return item.substring(item.indexOf("=") + 2, item.length() - 1);
            }
        }
        return "unknown_file";
    }

    // Helper to prevent XSS
    private String escapeHtml(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    // Helper to convert full ESRB rating names to their abbreviated forms
    private String convertToEsrbAbbreviation(String esrbRating) {
        switch (esrbRating) {
            case "Everyone":
                return "E";
            case "Everyone 10+":
                return "E10+";
            case "Teen":
                return "T";
            case "Mature":
                return "M";
            case "Adults Only":
                return "AO";
            case "Rating Pending":
                return "RP";
            default:
                return esrbRating;
        }
    }
}
