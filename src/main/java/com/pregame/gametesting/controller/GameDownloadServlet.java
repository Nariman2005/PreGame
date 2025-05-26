package com.pregame.gametesting.controller;

import com.pregame.gametesting.dao.GameDAO;
import com.pregame.gametesting.model.Game;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/games/download")
public class GameDownloadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the game ID from the request
        String gameIdStr = request.getParameter("gameId");

        if (gameIdStr == null || gameIdStr.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Game ID is required");
            return;
        }

        try {
            int gameId = Integer.parseInt(gameIdStr);

            // Get the game from the database (you'll need to implement this)
            Game game = GameDAO.getGameById(gameId);

            if (game == null || game.getDownloadUrl() == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Game or download URL not found");
                return;
            }

            // Extract the S3 key from the download URL
            String downloadUrl = game.getDownloadUrl();
            String s3Key = extractS3KeyFromUrl(downloadUrl);

            // Redirect to the S3DownloadServlet with the S3 key
            response.sendRedirect(request.getContextPath() + "/s3download/" + s3Key);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid game ID");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing download request");
        }
    }

    private String extractS3KeyFromUrl(String downloadUrl) {
        // Example URL: https://secure-file-share-bucket-cyber-knights.s3.eu-north-1.amazonaws.com/uploads/abc123_game.zip
        // Extract the key: uploads/abc123_game.zip

        if (downloadUrl == null) return null;

        // Find the position after the bucket name and domain
        int startIndex = downloadUrl.indexOf(".amazonaws.com/");
        if (startIndex < 0) return null;

        return downloadUrl.substring(startIndex + 14); // 14 is the length of ".amazonaws.com/"
    }
}