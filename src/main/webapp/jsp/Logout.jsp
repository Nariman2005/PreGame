<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Invalidate the session if it exists
    HttpSession userSession = request.getSession(false);
    if (userSession != null) {
        userSession.invalidate();
    }

    request.setAttribute("logoutSuccess", true);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Logged Out - PreGame Testing Platform</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <meta http-equiv="refresh" content="2;url=${pageContext.request.contextPath}/">
    <style>
        .logout-container {
            max-width: 600px;
            margin: 100px auto;
            text-align: center;
            padding: 30px;
            border-radius: 10px;
            background-color: #fff;
            box-shadow: 0 5px 15px rgba(0,0,0,0.08);
        }

        .logout-icon {
            font-size: 60px;
            color: #3a6df0;
            margin-bottom: 20px;
        }

        .logout-message {
            font-size: 24px;
            margin-bottom: 15px;
            color: #333;
        }

        .logout-redirect {
            color: #6e7891;
            font-size: 16px;
            margin-bottom: 20px;
        }

        .progress-bar-container {
            width: 100%;
            background-color: #f3f3f3;
            height: 5px;
            border-radius: 3px;
            margin-bottom: 10px;
        }

        .progress-bar {
            width: 0;
            height: 5px;
            background-color: #3a6df0;
            border-radius: 3px;
            animation: progress 2s linear forwards;
        }

        @keyframes progress {
            0% { width: 0; }
            100% { width: 100%; }
        }

        .home-link {
            display: inline-block;
            margin-top: 15px;
            color: #3a6df0;
            text-decoration: none;
            font-weight: 500;
        }

        .home-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="logout-container">
        <div class="logout-icon">
            <i class="fas fa-check-circle"></i>
        </div>
        <h1 class="logout-message">You have been logged out</h1>
        <p class="logout-redirect">Redirecting to the home page in a few seconds...</p>
        <div class="progress-bar-container">
            <div class="progress-bar"></div>
        </div>
        <a href="${pageContext.request.contextPath}/" class="home-link">
            <i class="fas fa-home"></i> Go to home page now
        </a>
    </div>

    <script>
        // Countdown timer for redirect (optional)
        let seconds = 2;
        const countdown = setInterval(function() {
            seconds--;
            if (seconds <= 0) {
                clearInterval(countdown);
            }

        }, 1000);
    </script>
</body>
</html>
