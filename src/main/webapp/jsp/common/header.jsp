<%-- header.jsp --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.pregame.gametesting.model.User" %>

<%-- ========== Java Initialization Block ========== --%>
<%
    // --- Page Title ---
    String pageTitle = request.getParameter("title");
    // Default title if not provided (e.g., if header is included without a title param)
    if (pageTitle == null || pageTitle.trim().isEmpty()) {
        // pageTitle = "PreGame Testing Platform"; // Default if you want one
        pageTitle = null; // Set to null if you DON'T want a default page-header if no title is passed
    }

    // --- User Session Data ---
    User user = null;
    String userName = null;
    String userRole = null; // Renamed from userType for clarity, assuming it's a role

    try {
        Object userObj = session.getAttribute("user");
        if (userObj instanceof User) {
            user = (User) userObj;
            userName = user.getName(); // Assuming User class has getName()
        }
        // It's generally better to store userRole in the User object itself,
        // but if it's a separate attribute:
        userRole = (String) session.getAttribute("userType"); // Or "userRole" if you rename the session attribute

    } catch (ClassCastException cce) {
        // This can happen if 'user' attribute is not a User object
        System.err.println("Header.jsp: ClassCastException retrieving user from session. Invalidating 'user' attribute. " + cce.getMessage());
        session.removeAttribute("user"); // Remove the problematic attribute
        // Reset local variables
        user = null;
        userName = null;
        // userRole might still be valid if fetched separately and didn't cause the CCE
    } catch (Exception e) {
        System.err.println("Header.jsp: Error retrieving user data from session: " + e.getMessage());
        // Reset local variables as a precaution
        user = null;
        userName = null;
        userRole = null; // Reset role as well in case of general error
        session.removeAttribute("user"); // Potentially clear if error is session-related
        session.removeAttribute("userType");
    }
%>

<%-- ========== CSS Styles ========== --%>
<%-- RECOMMENDED: Move these styles to an external CSS file (e.g., /css/header.css or /css/main.css) --%>
<%-- and link it in the main JSP's <head> or here if this header is self-contained. --%>
<style>
    .main-header {
        background-color: #ffffff;
        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        position: sticky;
        top: 0;
        z-index: 1000;
        padding: 15px 0;
    }

    .header-content {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }

    .logo a {
        display: flex;
        align-items: center;
        font-size: 22px;
        font-weight: 700;
        color: #3a6df0;
        text-decoration: none;
        transition: all 0.3s ease;
    }

    .logo img {
        height: 40px;
        margin-right: 10px;
    }

    .logo span {
        background: linear-gradient(135deg, #3a6df0, #5c86f6);
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
    }

    .nav-section {
        display: flex;
        align-items: center;
    }

    .main-nav ul {
        display: flex;
        list-style: none;
        margin: 0;
        padding: 0;
        gap: 25px;
    }

    .main-nav a {
        color: #333;
        font-weight: 500;
        text-decoration: none;
        font-size: 16px;
        transition: color 0.3s ease;
        position: relative;
        padding: 5px 0;
    }

    .main-nav a:hover {
        color: #3a6df0;
    }

    .main-nav a::after {
        content: '';
        position: absolute;
        bottom: 0;
        left: 0;
        width: 0;
        height: 2px;
        background-color: #3a6df0;
        transition: width 0.3s ease;
    }

    .main-nav a:hover::after {
        width: 100%;
    }

    .user-actions {
        display: flex;
        align-items: center;
        gap: 15px;
        margin-left: 30px;
    }

     .btn-register {
        padding: 8px 20px;
        border-radius: 6px;
        font-weight: 600;
        font-size: 14px;
        text-decoration: none;
        transition: all 0.3s ease;
    }


    .btn-register {
        color: #fff;
        background: linear-gradient(135deg, #3a6df0, #5c86f6);
        border: none;
    }

    .btn-register:hover {
        box-shadow: 0 5px 15px rgba(58, 109, 240, 0.3);
        transform: translateY(-2px);
    }

    .user-menu {
        position: relative;
    }

    .user-menu-trigger {
        display: flex;
        align-items: center;
        gap: 8px;
        padding: 8px 15px;
        border-radius: 30px;
        background-color: #f5f7fb;
        color: #333;
        text-decoration: none;
        font-weight: 500;
        transition: all 0.3s ease;
    }

    .user-menu-trigger:hover {
        background-color: #e8ecf5;
    }

    .user-dropdown {
        position: absolute;
        top: calc(100% + 10px);
        right: 0;
        width: 220px;
        background-color: #fff;
        border-radius: 8px;
        box-shadow: 0 5px 25px rgba(0, 0, 0, 0.15);
        opacity: 0;
        visibility: hidden;
        transform: translateY(10px);
        transition: all 0.3s ease;
        overflow: hidden;
        z-index: 1000; /* Ensure dropdown is on top */
    }

    .user-menu:hover .user-dropdown {
        opacity: 1;
        visibility: visible;
        transform: translateY(0);
    }

    .user-dropdown ul {
        list-style: none;
        margin: 0;
        padding: 8px 0;
    }

    .user-dropdown li a {
        display: flex;
        align-items: center;
        gap: 10px;
        padding: 12px 20px;
        color: #333;
        text-decoration: none;
        transition: all 0.2s ease;
    }

    .user-dropdown li a:hover {
        background-color: #f5f7fb;
        color: #3a6df0;
    }

    .user-dropdown li a i {
        width: 18px;
        text-align: center;
        color: #6e7891;
    }

    .notification-badge {
        position: relative;
    }

    .notification-badge::after {
        content: '';
        position: absolute;
        top: 0;
        right: 0;
        width: 8px;
        height: 8px;
        background-color: #ff5c5c;
        border-radius: 50%;
    }

    .menu-toggle {
        display: none; /* Hidden by default, shown in media query */
        background: none;
        border: none;
        color: #333;
        font-size: 24px;
        cursor: pointer;
    }

    /* Styles for the optional page title header */
    .page-header {
        background-color: #f5f7fb;
        padding: 30px 0;
        text-align: center;
        margin-bottom: 30px; /* Or remove if not needed */
    }

    .page-header h1 {
        color: #333;
        font-size: 28px;
        margin: 0;
    }

    /* Responsive Styles */
    @media (max-width: 992px) {
        .nav-section {
            position: fixed; /* Changed from absolute for better viewport handling */
            top: 0; /* Start from top if menu covers whole screen */
            left: -100%;
            width: 280px;
            height: 100vh;
            background-color: #fff;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.15);
            flex-direction: column;
            align-items: flex-start;
            padding: 20px;
            padding-top: 80px; /* Add padding to not overlap with a fixed main header */
            transition: left 0.3s ease;
            z-index: 999; /* Below main header if main header is fixed */
        }

        .nav-section.active {
            left: 0;
        }

        .main-nav ul {
            flex-direction: column;
            gap: 15px;
            width: 100%;
        }

        .main-nav a {
            display: block;
            padding: 10px 0;
        }

        .user-actions {
            margin-left: 0;
            margin-top: 20px;
            width: 100%;
            flex-direction: column; /* Stack buttons in mobile */
            gap: 10px;
        }
        .user-actions .btn-login,
        .user-actions .btn-register {
            width: 100%;
            text-align: center;
        }
        .user-actions .user-menu {
            width: 100%;
        }
        .user-actions .user-menu-trigger {
            justify-content: center;
        }


        .menu-toggle {
            display: block;
            z-index: 1001; /* Ensure toggle is above nav-section when it slides in */
        }
    }
</style>

<%-- ========== Main Header Structure ========== --%>
<header class="main-header">
    <div class="container">
        <div class="header-content">
            <%-- Logo --%>
            <div class="logo">
                <a href="${pageContext.request.contextPath}/">
                    <%-- Consider adding a real logo.png or ensure this path is correct --%>
                    <span>PreGame Testing</span>
                </a>
            </div>

            <%-- Mobile Menu Toggle Button --%>
            <button class="menu-toggle" id="menu-toggle" aria-label="Toggle navigation" aria-expanded="false" aria-controls="nav-section">
                <i class="fas fa-bars"></i>
            </button>

            <%-- Navigation Section (includes main nav and user actions) --%>
            <div class="nav-section" id="nav-section">
                <%-- Main Navigation --%>
                <nav class="main-nav" aria-label="Main navigation">
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/">Home</a></li>
                        <% if (userRole != null && ("gamer".equals(userRole) || "tester".equals(userRole))) { %>
                        <li><a href="${pageContext.request.contextPath}/games/browse">Games</a></li>
                        <% } %>
                        <%-- Conditional Links for Developer --%>
                        <% if (userRole != null && "developer".equals(userRole)) { %>
                        <li><a href="${pageContext.request.contextPath}/games/upload">Upload Game</a></li>
                        <li><a href="${pageContext.request.contextPath}/developer/dashboard">Dashboard</a></li>
                        <% } %>
                        <%-- Conditional Links for Gamer/Tester --%>
                        <% if (userRole != null && ("gamer".equals(userRole) || "tester".equals(userRole))) { %>
                        <li><a href="${pageContext.request.contextPath}/tester/dashboard">My Tests</a></li>
                        <% } %>
                        <li><a href="${pageContext.request.contextPath}/about">About Us</a></li>
                        <li><a href="${pageContext.request.contextPath}/contact">Contact</a></li>
                    </ul>
                </nav>

                <%-- User Actions (Login/Register or User Menu) --%>
                <div class="user-actions">
                    <% if (userName != null) { %>
                    <%-- Logged-in User Menu --%>
                    <div class="user-menu">
                        <a href="#" class="user-menu-trigger" aria-haspopup="true" aria-expanded="false">
                            <i class="fas fa-user-circle"></i>
                            <span><%= userName %></span>
                            <i class="fas fa-chevron-down" aria-hidden="true"></i>
                        </a>
                        <div class="user-dropdown">
                            <ul>
                                <li><a href="${pageContext.request.contextPath}/profile"><i class="fas fa-user"></i> My Profile</a></li>
                                <% if (userRole != null && "developer".equals(userRole)) { %>
                                <li><a href="${pageContext.request.contextPath}/games/upload"><i class="fas fa-upload"></i> Upload Game</a></li>
                                <% } %>
                                <li><a href="${pageContext.request.contextPath}/inbox"><i class="fas fa-inbox notification-badge"></i> Messages</a></li>
                                <li><a href="${pageContext.request.contextPath}/settings"><i class="fas fa-cog"></i> Settings</a></li>
                                <li><a href="${pageContext.request.contextPath}/logout"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
                            </ul>
                        </div>
                    </div>
                    <% } else { %>
                    <%-- Logged-out User Actions --%>
                    <a href="${pageContext.request.contextPath}/auth?action=login" class="btn-register">Login</a>
                    <a href="${pageContext.request.contextPath}/auth?action=register" class="btn-register">Register</a>
                    <% } %>
                </div>
            </div>
        </div>
    </div>
</header>

<%-- ========== Optional Page Title Header ========== --%>
<%-- This section displays the page title (e.g., "Home", "About Us") if `pageTitle` is set. --%>
<%-- To prevent "Home" from showing here (as per your image), ensure `pageTitle` is null or empty --%>
<%-- when including this header on the homepage, or modify the logic in the Java Init Block. --%>
<%-- Or, pass a different title like `param.title="Welcome"` and have specific condition here: --%>
<%-- <% if (pageTitle != null && !pageTitle.isEmpty() && !"Home".equals(pageTitle)) { %> --%>
<%--<% if (pageTitle != null && !pageTitle.trim().isEmpty()) { %>--%>
<%--<div class="page-header">--%>
<%--    <div class="container">--%>
<%--        <h1><%= pageTitle %></h1>--%>
<%--    </div>--%>
<%--</div>--%>
<%--<% } %>--%>

<%-- ========== JavaScript for Mobile Menu ========== --%>
<%-- RECOMMENDED: Move this script to an external JS file (e.g., /js/header.js or /js/main.js) --%>
<%-- and include it at the end of your main JSP's <body>. --%>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        const menuToggle = document.getElementById('menu-toggle');
        const navSection = document.getElementById('nav-section');

        if (menuToggle && navSection) { // Ensure elements exist
            menuToggle.addEventListener('click', function() {
                const isExpanded = navSection.classList.toggle('active');
                menuToggle.setAttribute('aria-expanded', isExpanded);

                // Change menu icon
                const icon = menuToggle.querySelector('i');
                if (isExpanded) {
                    icon.classList.remove('fa-bars');
                    icon.classList.add('fa-times');
                } else {
                    icon.classList.remove('fa-times');
                    icon.classList.add('fa-bars');
                }
            });
        } else {
            console.error("Menu toggle or navigation section not found.");
        }
    });
</script>