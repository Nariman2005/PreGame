package com.pregame.gametesting.controller;

import com.pregame.gametesting.dao.UserDAO;
import com.pregame.gametesting.model.User;
import com.pregame.gametesting.model.Gamer;
import com.pregame.gametesting.model.GameDeveloper;
import com.pregame.gametesting.model.Tester;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import jakarta.mail.MessagingException;

@WebServlet(name = "AuthController", urlPatterns = {"/auth", "/login", "/logout", "/profile"})
public class AuthController extends HttpServlet {
    private UserDAO userDAO;

    public AuthController() {
        this.userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getServletPath();

        if ("/profile".equals(pathInfo)) {
            showProfilePage(request, response);
            return;
        } else if ("/logout".equals(pathInfo)) {
            // Handle logout
            handleLogout(request, response);
            return;
        }
        
        String action = request.getParameter("action");

        if (action == null) {
            action = "login"; // Default action
        }

        switch (action) {
            case "login":
                showLoginForm(request, response);
                break;
            case "register":
                String type = request.getParameter("type");
                showRegistrationForm(request, response, type);
                break;

            default:
                response.sendRedirect(request.getContextPath() + "/");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        switch (action) {
            case "login":
                processLogin(request, response);
                break;
            case "register":
                processRegistration(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/");
                break;
        }
    }

    private void showLoginForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/auth/login.jsp").forward(request, response);
    }

    private void showRegistrationForm(HttpServletRequest request, HttpServletResponse response, String userType)
            throws ServletException, IOException {

        if (userType == null) {
            // Show registration options page
            request.getRequestDispatcher("/jsp/auth/register_choice.jsp").forward(request, response);
            return;
        }

        // Set user type in request for form population
        request.setAttribute("userType", userType);

        switch (userType) {
            case "gamer":
                request.getRequestDispatcher("/jsp/auth/gamer_register.jsp").forward(request, response);
                break;
            case "developer":
                request.getRequestDispatcher("/jsp/auth/developer_register.jsp").forward(request, response);
                break;
            case "tester":
                request.getRequestDispatcher("/jsp/auth/tester_register.jsp").forward(request, response);
                break;
            case "admin":
                request.getRequestDispatcher("/jsp/auth/Admin.jsp").forward(request, response);
                break;
            default:
                // Invalid user type, redirect to registration choice
                response.sendRedirect(request.getContextPath() + "/auth?action=register");
                break;
        }
    }

    private void processLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");



        // Validate input
        if (email == null || password == null || email.trim().isEmpty() || password.trim().isEmpty()) {
            request.setAttribute("error", "Email and password are required");
            request.getRequestDispatcher("/jsp/auth/login.jsp").forward(request, response);
            return;
        }
        else if (userDAO.isAdmin(email, password)) {
            request.getRequestDispatcher("/jsp/auth/Admin.jsp").forward(request, response);
        }


        try {
            System.out.println("Calling UserDAO.authenticate()");
            User user = userDAO.authenticate(email, password);
            System.out.println("Authentication result: " + (user != null ? "Success" : "Failed"));

            if (user != null) {
                // Authentication successful
                HttpSession session = request.getSession();
                session.setMaxInactiveInterval(60 * 30); // Set to 30 minutes

                // Store the entire user object in the session
                session.setAttribute("user", user);

                // Use consistent naming for session attributes
                // The header.jsp uses userName, so make sure that's set correctly
                session.setAttribute("userName", user.getName());
                session.setAttribute("userEmail", user.getEmail());
                session.setAttribute("userType", user.getUserType());
                session.setAttribute("userId", user.getId());

                System.out.println("Login successful for: " + user.getName() + " (" + user.getUserType() + ")");
                System.out.println("Session ID: " + session.getId());
                System.out.println("Session attributes: userName=" + session.getAttribute("userName") + ", userType=" + session.getAttribute("userType"));
                System.out.println("Redirecting to: " + request.getContextPath() + "/games/browse");

                // Ensure redirect to browse page after successful login
                response.sendRedirect(request.getContextPath() + "/games/browse");
            } else {
                // Authentication failed
                System.out.println("Authentication failed for email: " + email);
                request.setAttribute("error", "Invalid email or password");
                request.getRequestDispatcher("/jsp/auth/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("=======================");
            request.setAttribute("error", "Login failed: " + e.getMessage());
            request.getRequestDispatcher("/jsp/auth/login.jsp").forward(request, response);
        }
    }


    private void processRegistration(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userType = request.getParameter("userType");

        if (userType == null) {
            response.sendRedirect(request.getContextPath() + "/auth?action=register");
            return;
        }

        try {
            // Create and populate RegistrationBean from request parameters
            com.pregame.gametesting.beans.RegistrationBean registrationBean = new com.pregame.gametesting.beans.RegistrationBean();
            registrationBean.setUserType(userType);
            registrationBean.setName(request.getParameter("name"));
            registrationBean.setEmail(request.getParameter("email"));
            registrationBean.setPassword(request.getParameter("password"));
            registrationBean.setConfirmPassword(request.getParameter("confirmPassword"));
            registrationBean.setCountryCode(request.getParameter("countryCode"));
            registrationBean.setTelephone(request.getParameter("telephone"));

            try {
                registrationBean.setAge(Integer.parseInt(request.getParameter("age")));
            } catch (NumberFormatException e) {
                registrationBean.setAge(0); // Will fail validation
            }

            // Set user type specific fields
            if ("gamer".equals(userType)) {
                try {
                    registrationBean.setLevel(Integer.parseInt(request.getParameter("level")));
                } catch (NumberFormatException e) {
                    registrationBean.setLevel(0); // Will fail validation
                }
            } else if ("developer".equals(userType)) {
                registrationBean.setCompanyName(request.getParameter("companyName"));
            } else if ("tester".equals(userType)) {
                registrationBean.setRank(request.getParameter("rank"));
            }

            // Validate the registration bean
            String validationError = registrationBean.validate();
            if (validationError != null) {
                // If validation fails, store bean in request and show registration form
                request.setAttribute("error", validationError);
                request.setAttribute("registrationBean", registrationBean);
                showRegistrationForm(request, response, userType);
                return;
            }

            // Create the user object from validated bean
            User user = createUserFromBean(registrationBean);

            if (user != null) {
                int userId = userDAO.registerUser(user);

                if (userId > 0) {
                    // Registration successful
                    request.setAttribute("success", "Registration successful! Please check your email for confirmation and log in.");

                    // Send welcome email to the user
                    try {
                        String emailSubject = "Welcome to PreGame Testing Platform";
                        String emailBody = "Dear " + user.getName() + ",\n\n"
                                + "Thank you for registering with the PreGame Testing Platform as a " + formatUserType(userType)
                                + ".\n\n"
                                + "Your account has been successfully created. You can now log in using your email and password.\n\n"
                                + "If you have any questions or need assistance, please don't hesitate to contact us.\n\n"
                                + "Best regards,\n"
                                + "The PreGame Team";

                        MailSender.send(user.getEmail(), emailSubject, emailBody);
                    } catch (MessagingException e) {
                        // Log the error but don't stop the registration process
                        System.err.println("Failed to send welcome email: " + e.getMessage());
                    }

                    request.getRequestDispatcher("/WEB-INF/jsp/auth/login.jsp").forward(request, response);
                } else {
                    // Registration failed
                    request.setAttribute("error", "Registration failed");
                    request.setAttribute("registrationBean", registrationBean);
                    showRegistrationForm(request, response, userType);
                }
            } else {
                // Invalid user data
                request.setAttribute("error", "Invalid registration data");
                request.setAttribute("registrationBean", registrationBean);
                showRegistrationForm(request, response, userType);
            }
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            showRegistrationForm(request, response, userType);
        } catch (Exception e) {
            request.setAttribute("error", "Registration failed: " + e.getMessage());
            showRegistrationForm(request, response, userType);
        }
    }

    // Helper method to create User from RegistrationBean
    private User createUserFromBean(com.pregame.gametesting.beans.RegistrationBean bean) {
        String userType = bean.getUserType();

        // Create specific user type
        User user = null;

        switch (userType) {
            case "gamer":
                Gamer gamer = new Gamer(bean.getName(), bean.getPassword(), bean.getEmail());
                gamer.setAge(bean.getAge());
                gamer.setCountryCode(bean.getCountryCode());
                gamer.setTelephone(bean.getTelephone());
                gamer.setLevel(bean.getLevel());
                user = gamer;
                break;

            case "developer":
                GameDeveloper developer = new GameDeveloper(bean.getName(), bean.getPassword(), bean.getEmail());
                developer.setAge(bean.getAge());
                developer.setCountryCode(bean.getCountryCode());
                developer.setTelephone(bean.getTelephone());
                developer.setCompanyName(bean.getCompanyName());
                user = developer;
                break;

            case "tester":
                Tester tester = new Tester(bean.getName(), bean.getPassword(), bean.getEmail());
                tester.setAge(bean.getAge());
                tester.setCountryCode(bean.getCountryCode());
                tester.setTelephone(bean.getTelephone());
                tester.setRank(bean.getRank());
                user = tester;
                break;
        }

        return user;
    }

    // Helper method to format user type for email
    private String formatUserType(String userType) {
        switch (userType) {
            case "gamer":
                return "Gamer";
            case "developer":
                return "Game Developer";
            case "tester":
                return "Tester";
            default:
                return "User";
        }
    }

    private void showProfilePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get user information from session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String userType = (String) session.getAttribute("userType");

        // Check if user is logged in
        if (user == null) {
            // Redirect to login if not logged in
            response.sendRedirect(request.getContextPath() + "/auth?action=login");
            return;
        }

        // Set user attributes for the profile page
        request.setAttribute("username", user.getName());
        request.setAttribute("email", user.getEmail());

        // Redirect to appropriate profile page based on user type
        String destination;
        if ("developer".equalsIgnoreCase(userType)) {
            destination = "/jsp/Profiles/developer_profile.jsp";
        } else if ("gamer".equalsIgnoreCase(userType)) {
            // Use the gamer profile page for all non-developer users
            destination = "/jsp/Profiles/gamer_profile.jsp";
        }else{
            destination = "/jsp/Profiles/tester_profile.jsp";

            try {
                // Add required attributes for gamer profile
                // Create a map for gamer profile data
                Map<String, Object> gamerProfile = new HashMap<>();
                gamerProfile.put("username", user.getName());
                gamerProfile.put("profilePicture", "/images/default-profile.png");
                gamerProfile.put("joinDate", "January 1, 2023"); // Default join date
                gamerProfile.put("gamesTested", 0); // Default value
                gamerProfile.put("reviewCount", 0); // Default value

                request.setAttribute("gamer", gamerProfile);
                request.setAttribute("reviews", new ArrayList<>()); // Empty reviews list
                request.setAttribute("availableGames", new ArrayList<>()); // Empty games list

            } catch (Exception e) {
                System.err.println("Error preparing gamer profile data: " + e.getMessage());
                e.printStackTrace();

                // Use default values in case of error
                Map<String, Object> defaultGamerProfile = new HashMap<>();
                defaultGamerProfile.put("username", user.getName());
                defaultGamerProfile.put("profilePicture", "/images/default-profile.png");
                defaultGamerProfile.put("joinDate", "Unknown");
                defaultGamerProfile.put("gamesTested", 0);
                defaultGamerProfile.put("reviewCount", 0);

                request.setAttribute("gamer", defaultGamerProfile);
                request.setAttribute("reviews", new ArrayList<>());
                request.setAttribute("availableGames", new ArrayList<>());
            }
        }

        request.getRequestDispatcher(destination).forward(request, response);
    }

    private void handleLogout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set cache control headers to prevent browser caching
        // These are important to ensure the browser doesn't show cached pages after logout
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setDateHeader("Expires", 0); // Proxies.

        HttpSession session = request.getSession(false); // Get the session, don't create if it doesn't exist
        if (session != null) {
            System.out.println("Invalidating session ID: " + session.getId()); // Optional: for logging
            session.invalidate(); // Invalidate the session, removing all attributes
            System.out.println("Session invalidated."); // Optional: for logging
        } else {
            System.out.println("No active session to invalidate."); // Optional: for logging
        }

        // Forward to logout page (which might then do a client-side redirect via meta tag or JS)
        request.getRequestDispatcher("/jsp/Logout.jsp").forward(request, response);
    }
}
