package com.pregame.gametesting.controller;
import com.pregame.gametesting.util.AWSS3Utility;
import java.io.InputStream;
import com.pregame.gametesting.dao.GameDAO;
import com.pregame.gametesting.model.Game;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;

@WebServlet("/games/download")
public class GameDownloadServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(GameDownloadServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processDownloadRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processDownloadRequest(request, response);
    }

    protected void processDownloadRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get the game ID from the request
        String gameIdStr = request.getParameter("id");

        logger.info("Download request received for game ID: " + gameIdStr);

        if (gameIdStr == null || gameIdStr.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Game ID is required");
            return;
        }

        try {
            int gameId = Integer.parseInt(gameIdStr);

            // Get the game from the database
            GameDAO gameDAO = new GameDAO();
            Game game = gameDAO.getGameById(gameId);

            if (game == null || game.getDownloadUrl() == null) {
                logger.warning("Game or download URL not found for ID: " + gameId);
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Game or download URL not found");
                return;
            }

            logger.info("Found game with download URL: " + game.getDownloadUrl());

            // Extract the S3 key from the download URL
            String s3Key = extractS3KeyFromUrl(game.getDownloadUrl());
            logger.info("Extracted S3 key: " + s3Key);

            if (s3Key == null) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Invalid download URL format");
                return;
            }

            // Get file information
            AWSS3Utility.S3ObjectInfo objectInfo = AWSS3Utility.getObjectInfo(s3Key);
            if (objectInfo == null) {
                logger.warning("File not found in S3 storage with key: " + s3Key);
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found in storage");
                return;
            }

            // Get filename from the key
            String fileName = s3Key.substring(s3Key.lastIndexOf('/') + 1);
            if (fileName.contains("_")) {
                // Remove UUID prefix if present (format: UUID_filename.ext)
                fileName = fileName.substring(fileName.indexOf('_') + 1);
            }

            logger.info("Preparing download for file: " + fileName);

            // Set response headers
            response.setContentType(objectInfo.getContentType());
            response.setContentLengthLong(objectInfo.getContentLength());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

            // Get file stream and copy to response
            try (InputStream fileStream = AWSS3Utility.downloadFile(s3Key)) {
                if (fileStream == null) {
                    logger.warning("Download stream could not be opened for key: " + s3Key);
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found in storage");
                    return;
                }

                // Copy the file data to the response
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = fileStream.read(buffer)) != -1) {
                    response.getOutputStream().write(buffer, 0, bytesRead);
                }

                logger.info("Download completed successfully for game ID: " + gameId);
            }

        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Invalid game ID format", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid game ID");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error processing download request", e);
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing download request: " + e.getMessage());
        }
    }

    private String extractS3KeyFromUrl(String downloadUrl) {
        // Example URL: https://secure-file-share-bucket-cyber-knights.s3.eu-north-1.amazonaws.com/uploads/abc123_game.zip
        // Extract the key: uploads/abc123_game.zip

        if (downloadUrl == null) return null;

        logger.info("Parsing S3 URL: " + downloadUrl);

        try {
            // Find the position after the bucket name and domain
            int startIndex = downloadUrl.indexOf(".amazonaws.com/");
            if (startIndex < 0) {
                // Try alternative format that might be stored in the database
                startIndex = downloadUrl.indexOf(AWSS3Utility.getBucketName() + "/");
                if (startIndex < 0) {
                    logger.warning("Could not parse S3 URL format: " + downloadUrl);
                    return null;
                }
                return downloadUrl.substring(startIndex + AWSS3Utility.getBucketName().length() + 1);
            }

            String key = downloadUrl.substring(startIndex + 14); // 14 is the length of ".amazonaws.com/"
            logger.info("Extracted key: " + key);
            return key;
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error parsing S3 URL", e);
            return null;
        }
    }
}

