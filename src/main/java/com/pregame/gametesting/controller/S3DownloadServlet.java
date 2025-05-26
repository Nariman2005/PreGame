package com.pregame.gametesting.controller;

import com.pregame.gametesting.util.AWSS3Utility;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * Servlet for downloading files from S3 bucket
 */
@WebServlet("/s3download/*")
public class S3DownloadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("[S3DownloadServlet] Received request for: " + request.getRequestURI());

        // Extract the path info (key of the object in S3)
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.isEmpty() || "/".equals(pathInfo)) {
            System.err.println("[S3DownloadServlet] Invalid path info: " + pathInfo);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // Remove leading slash and decode the URL
        if (pathInfo.startsWith("/")) {
            pathInfo = pathInfo.substring(1);
        }
        String s3Key = URLDecoder.decode(pathInfo, StandardCharsets.UTF_8);

        System.out.println("[S3DownloadServlet] Attempting to download S3 object with key: " + s3Key);

        try {
            // Get the S3 object's metadata first
            AWSS3Utility.S3ObjectInfo objectInfo = AWSS3Utility.getObjectInfo(s3Key);

            if (objectInfo == null) {
                System.err.println("[S3DownloadServlet] Object not found in S3: " + s3Key);
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            // Set content type if available
            if (objectInfo.getContentType() != null) {
                response.setContentType(objectInfo.getContentType());
            } else {
                // Fallback to determining content type from filename
                String contentType = getServletContext().getMimeType(s3Key);
                response.setContentType(contentType != null ? contentType : "application/octet-stream");
            }

            // Set content length if available
            if (objectInfo.getContentLength() > 0) {
                response.setContentLengthLong(objectInfo.getContentLength());
            }

            // Extract filename from key - use last part after '/'
            String filename = s3Key;
            if (filename.contains("/")) {
                filename = filename.substring(filename.lastIndexOf('/') + 1);
            }

            // Set content disposition for download
            response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

            System.out.println("[S3DownloadServlet] Streaming S3 object: " + filename);

            // Stream the S3 object to the response output stream
            try (InputStream s3Stream = AWSS3Utility.downloadFile(s3Key);
                 OutputStream out = response.getOutputStream()) {

                if (s3Stream == null) {
                    System.err.println("[S3DownloadServlet] Failed to get stream from S3: " + s3Key);
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }

                byte[] buffer = new byte[8192]; // 8KB buffer
                int bytesRead;

                while ((bytesRead = s3Stream.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }

                out.flush();
                System.out.println("[S3DownloadServlet] File sent successfully");
            }
        } catch (Exception e) {
            System.err.println("[S3DownloadServlet] Error downloading file from S3: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error downloading file: " + e.getMessage());
        }
    }
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Print the actual method being used
        System.out.println("[S3DownloadServlet] Request method: " + request.getMethod());

        if ("POST".equalsIgnoreCase(request.getMethod())) {
            doPost(request, response);
        } else {
            super.service(request, response);
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Log that we received a POST request
        System.out.println("[S3DownloadServlet] Handling POST request - delegating to doGet");

        // Simply delegate to doGet method to handle the download
        doGet(request, response);
    }
}