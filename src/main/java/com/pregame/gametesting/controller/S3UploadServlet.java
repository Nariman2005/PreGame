package com.pregame.gametesting.controller;

import com.pregame.gametesting.util.AWSS3Utility;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;

@WebServlet("/s3upload")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,     // 1 MB
        maxFileSize = 1024 * 1024 * 50,      // 50 MB
        maxRequestSize = 1024 * 1024 * 100    // 100 MB
)
public class S3UploadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Get form fields
            String gameTitle = escapeHtml(request.getParameter("gameTitle"));
            String gameDescription = escapeHtml(request.getParameter("gameDescription"));
            String gameGenre = escapeHtml(request.getParameter("gameGenre"));
            String gameVersion = escapeHtml(request.getParameter("gameVersion"));
            String esrbRating = escapeHtml(request.getParameter("esrbRating"));

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

            // Store game metadata in request attributes
            request.setAttribute("uploadSuccess", true);
            request.setAttribute("gameTitle", gameTitle);
            request.setAttribute("gameDescription", gameDescription);
            request.setAttribute("gameGenre", gameGenre);
            request.setAttribute("gameVersion", gameVersion);
            request.setAttribute("esrbRating", esrbRating);
            request.setAttribute("fileName", fileName);
            request.setAttribute("fileSize", fileSize);
            request.setAttribute("downloadUrl", downloadUrl);

            // Forward to result page
            request.getRequestDispatcher("/WEB-INF/jsp/s3upload-result.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "File upload failed: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
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
}