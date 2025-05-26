package com.pregame.gametesting.controller;

import com.pregame.gametesting.util.CsvUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/about", "/contact"})
public class Static_PageController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the requested URI path
        String path = request.getRequestURI().substring(request.getContextPath().length());

        // Route to the appropriate JSP based on the path
        String destination;

        switch (path) {
            case "/about":
                destination = "/jsp/about.jsp";
                break;
            case "/contact":
                destination = "/jsp/contact.jsp";
                break;
            case "/profile":
                // Get user type from the session
                String userType = (String) request.getSession().getAttribute("userType");

                if ("developer".equalsIgnoreCase(userType)) {
                    destination = "/jsp/Profiles/developer_profile.jsp";
                } else {
                    destination = "/jsp/Profiles/gamer_profile.jsp";
                }
                break;
                 default:
                destination = "/index.jsp";
                break;
        }

        // Forward the request to the JSP
        request.getRequestDispatcher(destination).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the requested URI path
        String path = request.getRequestURI().substring(request.getContextPath().length());

        if ("/contact".equals(path)) {
            // Process the contact form submission

            // Get form data
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String subject = request.getParameter("subject");
            String message = request.getParameter("message");


            // Validate form data
            boolean isValid = validateContactForm(name, email, subject, message);

            if (isValid) {
                // Save to CSV file
                boolean saved = CsvUtil.saveContactMessage(name, email, subject, message);
                if (saved) {
                    // Show success message
                    request.setAttribute("successMessage", "Thank you! Your message has been received. We'll get back to you soon.");
                } else {
                    // Show error message
                    request.setAttribute("errorMessage", "Sorry, there was an issue processing your request. Please try again later.");
                }
            } else {
                // Show validation error
                request.setAttribute("errorMessage", "Please fill in all fields with valid information.");
            }

            // Return to the contact page with appropriate message
            request.getRequestDispatcher("/jsp/contact.jsp").forward(request, response);
        } else {
            // For any other POST requests, redirect to home
            response.sendRedirect(request.getContextPath() + "/");
        }
    }

    /**
     * Validates the contact form data
     */
    private boolean validateContactForm(String name, String email, String subject, String message) {
        // Check if all required fields are present and not empty
        if (name == null || email == null || subject == null || message == null) {
            return false;
        }

        if (name.trim().isEmpty() || email.trim().isEmpty() ||
            subject.trim().isEmpty() || message.trim().isEmpty()) {
            return false;
        }

        // Basic email validation
        if (!email.contains("@") || !email.contains(".")) {
            return false;
        }

        return true;
    }
}
