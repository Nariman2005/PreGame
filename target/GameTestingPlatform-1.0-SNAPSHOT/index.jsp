<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Date, java.text.SimpleDateFormat" %>
<%!
    // Method to get greeting based on time of day
    private String getDayTimeGreeting() {
        int hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY);

        if (hour < 12) {
            return "Good morning";
        } else if (hour < 18) {
            return "Good afternoon";
        } else {
            return "Good evening";
        }
    }
%>

<%
    // Calculate statistics for display
    int totalDevelopers = 120; // Simulated data
    int totalTesters = 350;    // Simulated data
    int totalGames = 275;      // Simulated data

    // Generate current date for display
    SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy");
    String currentDate = dateFormat.format(new Date());

    // Check if user is logged in (simulated from index.jsp, header.jsp uses its own 'user' object)
    String username = (String) session.getAttribute("username"); // This 'username' is specific to index.jsp's logic
    boolean isLoggedIn = (username != null);                     // The header.jsp has its own logic for 'userName' from the 'User' object
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PreGame Testing Platform</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<%--    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">--%>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <style>
        /* Styles for commented out .platform-features, kept for completeness if you uncomment them */
        .platform-features {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 30px;
            margin-top: 40px;
        }

        .feature-card {
            background-color: #fff;
            border-radius: 10px;
            padding: 30px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.08);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            text-align: center;
        }

        .feature-card:hover {
            transform: translateY(-10px);
            box-shadow: 0 15px 30px rgba(0,0,0,0.12);
        }

        .feature-icon {
            font-size: 48px;
            color: #3a6df0;
            margin-bottom: 20px;
        }

        .feature-title {
            font-size: 24px;
            margin-bottom: 15px;
            color: #333;
        }

        .feature-description {
            color: #6e7891;
            line-height: 1.6;
            margin-bottom: 20px;
        }

        .feature-link {
            display: inline-block;
            padding: 10px 20px;
            background-color: #3a6df0;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            font-weight: 500;
            transition: background-color 0.3s ease;
        }

        .feature-link:hover {
            background-color: #2a5cd0;
        }

        /* Basic container style often used in main content areas */
        .container {
            width: 90%;
            max-width: 1200px; /* Or your preferred max-width */
            margin-left: auto;
            margin-right: auto;
            padding-left: 15px;
            padding-right: 15px;
        }

        /* Utility classes for text alignment and spacing (example from stats section) */
        .text-center { text-align: center; }
        .mb-4 { margin-bottom: 1.5rem; } /* Example, adjust as needed */
        .mb-2 { margin-bottom: 0.5rem; }
        .py-5 { padding-top: 3rem; padding-bottom: 3rem; }
        .bg-light { background-color: #f8f9fa; } /* Example light background */

    </style>
</head>
<body>

<%-- ========== HEADER INCLUDE ========== --%>
<%-- This includes your header.jsp. --%>
<%-- Passing a title with just a space (or empty string) will prevent the header.jsp --%>
<%-- from rendering its own .page-header H1 title bar, as desired for the homepage. --%>
<jsp:include page="/jsp/common/header.jsp">
    <jsp:param name="title" value="" />
</jsp:include>

<%-- Original commented-out sections from your code --%>
<%--<section class="hero-section">--%>
<%--    <div class="container">--%>
<%--        <div class="hero-content">--%>
<%--            <h1>Welcome to PreGame Testing Platform</h1>--%>
<%--            <p>The ultimate platform connecting game developers with testers and gamers</p>--%>
<%--            <div class="hero-buttons">--%>
<%--                <a href="${pageContext.request.contextPath}/auth?action=login" class="btn btn-primary">Login</a>--%>
<%--                <a href="${pageContext.request.contextPath}/auth?action=register" class="btn btn-secondary">Register</a>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</section>--%>

<%--<section class="features-section">--%>
<%--    <div class="container">--%>
<%--        <h2 class="section-title">Platform Features</h2>--%>
<%--        <div class="platform-features"> ... cards ... </div>--%>
<%--    </div>--%>
<%--</section>--%>

<%-- ========== MAIN CONTENT AREA ========== --%>
<main>
    <%-- ========== HERO SECTION (This is the block you highlighted) ========== --%>
    <%-- This section is immediately after the header (via include) and is the first part of your <main> content. --%>
    <section style="background: linear-gradient(135deg, #3a6df0, #5c86f6); padding: 4rem 0; position: relative; overflow: hidden;">
        <!-- Background decorative elements -->
        <div style="position: absolute; width: 300px; height: 300px; background: rgba(255,255,255,0.05); border-radius: 50%; top: -100px; right: -100px;"></div>
        <div style="position: absolute; width: 200px; height: 200px; background: rgba(255,255,255,0.05); border-radius: 50%; bottom: -70px; left: 10%;"></div>

        <%-- The .container div you specifically wanted to place under the header --%>
        <div class="container">
            <div style="display: flex; justify-content: space-between; align-items: center; gap: 2rem; flex-wrap: wrap;">
                <div style="max-width: 600px; color: white; position: relative; z-index: 2;">
                    <div style="display: inline-block; background: rgba(255,255,255,0.15); padding: 0.5rem 1rem; border-radius: 50px; margin-bottom: 1rem;">
                        <span style="font-weight: 500; font-size: 0.9rem;">🚀 #1 Game Testing Platform</span>
                    </div>

                    <% if(isLoggedIn) { %>
                    <h1 style="font-size: 2.8rem; font-weight: 800; margin-bottom: 1.5rem; line-height: 1.2;"><%= getDayTimeGreeting() %>, <span style="color: #ffffff; text-decoration: underline; text-decoration-color: rgba(255,255,255,0.3); text-decoration-thickness: 4px;"><%= username %></span>!</h1>
                    <% } else { %>
                    <h1 style="font-size: 2.8rem; font-weight: 800; margin-bottom: 1.5rem; line-height: 1.2;"><%="Connect. Test. Play."%></h1>
                    <% } %>

                    <p style="font-size: 1.2rem; margin-bottom: 2rem; line-height: 1.6; opacity: 0.9;">The ultimate platform where game developers, testers, and gamers come together to create exceptional gaming experiences.</p>

                    <div style="display: flex; gap: 1rem; margin-bottom: 2rem;">
                        <a href="${pageContext.request.contextPath}/auth?action=register" style="background: white; color: #3a6df0; padding: 0.8rem 1.5rem; border-radius: 8px; font-weight: 600; text-decoration: none; box-shadow: 0 5px 15px rgba(0,0,0,0.1); display: inline-flex; align-items: center; transition: transform 0.2s;">
                            <i class="fas fa-rocket" style="margin-right: 8px;"></i> Get Started
                        </a>
                        <a href="#learn-more" style="background: rgba(255,255,255,0.15); color: white; padding: 0.8rem 1.5rem; border-radius: 8px; font-weight: 600; text-decoration: none; display: inline-flex; align-items: center; transition: transform 0.2s;">
                            <i class="fas fa-info-circle" style="margin-right: 8px;"></i> Learn More
                        </a>
                    </div>

                    <div style="display: flex; align-items: center; font-size: 0.9rem; opacity: 0.8;">
                        <i class="fas fa-calendar-alt" style="margin-right: 6px;"></i>
                        <span>Today is <%= currentDate %></span>
                    </div>
                </div>
            </div>

            <!-- Trusted by companies section (part of the hero section) -->
            <div style="margin-top: 3rem; border-top: 1px solid rgba(255,255,255,0.1); padding-top: 1.5rem; text-align: center; color: white;">
                <p style="font-size: 0.9rem; margin-bottom: 1rem; opacity: 0.7;">TRUSTED BY LEADING GAME COMPANIES</p>
                <div style="display: flex; justify-content: center; gap: 2rem; align-items: center; flex-wrap: wrap;">
                    <div style="opacity: 0.7;">Unity</div>
                    <div style="opacity: 0.7;">Epic Games</div>
                    <div style="opacity: 0.7;">EA Sports</div>
                    <div style="opacity: 0.7;">Ubisoft</div>
                    <div style="opacity: 0.7;">Activision</div>
                </div>
            </div>
        </div>
    </section> <%-- End of Hero Section --%>

    <%-- ========== STATS SECTION ========== --%>
    <section class="py-5 bg-light">
        <div class="container">
            <h2 class="text-center mb-4">Platform Statistics</h2>
            <div class="text-center mb-2">
                <div style="height: 3px; width: 80px; background-color: #3a6df0; margin: 0 auto; border-radius: 3px;"></div>
            </div>

            <div style="display: flex; flex-wrap: wrap; justify-content: center; gap: 30px; margin-top: 30px;">
                <!-- Developers Card -->
                <div style="background-color: white; border-radius: 10px; padding: 25px; text-align: center; box-shadow: 0 5px 15px rgba(0,0,0,0.08); flex: 1; min-width: 200px; max-width: 300px;">
                    <i class="fas fa-laptop-code" style="font-size: 40px; color: #3a6df0; margin-bottom: 15px;"></i>
                    <h3 style="font-size: 42px; font-weight: 700; color: #3a6df0; margin: 0;"><%= totalDevelopers %></h3>
                    <p style="text-transform: uppercase; font-weight: 600; color: #6e7891; letter-spacing: 1px; margin-top: 5px;">Game Developers</p>
                    <div style="margin-top: 15px;">
                        <span style="background-color: rgba(0,200,150,0.1); color: #00c896; padding: 5px 10px; border-radius: 4px; font-size: 12px;">
                            <i class="fas fa-arrow-up" style="margin-right: 5px;"></i>12% Growth
                        </span>
                    </div>
                </div>

                <!-- Testers Card -->
                <div style="background-color: white; border-radius: 10px; padding: 25px; text-align: center; box-shadow: 0 5px 15px rgba(0,0,0,0.08); flex: 1; min-width: 200px; max-width: 300px;">
                    <i class="fas fa-vial" style="font-size: 40px; color: #3a6df0; margin-bottom: 15px;"></i>
                    <h3 style="font-size: 42px; font-weight: 700; color: #3a6df0; margin: 0;"><%= totalTesters %></h3>
                    <p style="text-transform: uppercase; font-weight: 600; color: #6e7891; letter-spacing: 1px; margin-top: 5px;">Professional Testers</p>
                    <div style="margin-top: 15px;">
                        <span style="background-color: rgba(0,200,150,0.1); color: #00c896; padding: 5px 10px; border-radius: 4px; font-size: 12px;">
                            <i class="fas fa-arrow-up" style="margin-right: 5px;"></i>20% Growth
                        </span>
                    </div>
                </div>

                <!-- Games Card -->
                <div style="background-color: white; border-radius: 10px; padding: 25px; text-align: center; box-shadow: 0 5px 15px rgba(0,0,0,0.08); flex: 1; min-width: 200px; max-width: 300px;">
                    <i class="fas fa-gamepad" style="font-size: 40px; color: #3a6df0; margin-bottom: 15px;"></i>
                    <h3 style="font-size: 42px; font-weight: 700; color: #3a6df0; margin: 0;"><%= totalGames %></h3>
                    <p style="text-transform: uppercase; font-weight: 600; color: #6e7891; letter-spacing: 1px; margin-top: 5px;">Games Published</p>
                    <div style="margin-top: 15px;">
                        <span style="background-color: rgba(0,200,150,0.1); color: #00c896; padding: 5px 10px; border-radius: 4px; font-size: 12px;">
                            <i class="fas fa-arrow-up" style="margin-right: 5px;"></i>15% Growth
                        </span>
                    </div>
                </div>
            </div>
        </div>
    </section> <%-- End of Stats Section --%>
        <%-- ========== FEATURES SECTION ========== --%>
        <section id="learn-more" class="py-5">
            <div class="container">
                <h2 class="text-center mb-4">Platform Features</h2>
                <div class="text-center mb-4">
                    <div style="height: 3px; width: 80px; background-color: #3a6df0; margin: 0 auto; border-radius: 3px;"></div>
                </div>

                <div class="platform-features">
                    <!-- Game Testing Feature -->
                    <div class="feature-card">
                        <div class="feature-icon">
                            <i class="fas fa-gamepad"></i>
                        </div>
                        <h3 class="feature-title">Game Testing</h3>
                        <p class="feature-description">
                            Access early versions of games and provide valuable feedback to developers.
                        </p>
                        <a href="${pageContext.request.contextPath}/games" class="feature-link">Browse Games</a>
                    </div>

                    <!-- Feedback System Feature -->
                    <div class="feature-card">
                        <div class="feature-icon">
                            <i class="fas fa-comments"></i>
                        </div>
                        <h3 class="feature-title">Provide Feedback</h3>
                        <p class="feature-description">
                            Testers can submit detailed feedback to help developers improve their games.
                        </p>
                        <a href="${pageContext.request.contextPath}/games/feedback" class="feature-link">Provide Feedback</a>
                    </div>

                    <!-- Developer Tools Feature -->
                    <div class="feature-card">
                        <div class="feature-icon">
                            <i class="fas fa-code"></i>
                        </div>
                        <h3 class="feature-title">Developer Tools</h3>
                        <p class="feature-description">
                            Upload your games and receive comprehensive feedback from real players.
                        </p>
                        <a href="${pageContext.request.contextPath}/upload-game" class="feature-link">Upload Game</a>
                    </div>
                </div>
            </div>
        </section>
</main> <%-- End of Main Content Area --%>


<jsp:include page="/jsp/common/footer.jsp" />

</body>
</html>