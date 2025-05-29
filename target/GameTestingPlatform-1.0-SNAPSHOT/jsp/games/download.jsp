<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.pregame.gametesting.model.Game" %>
<%@ page import="com.pregame.gametesting.model.User" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Download Game | PreGame Testing Platform</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/js/all.min.js"></script>
    <style>
        .download-container {
            max-width: 800px;
            margin: 0 auto;
            padding: 30px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }
        
        .game-header {
            display: flex;
            align-items: center;
            margin-bottom: 30px;
        }
        
        .game-icon {
            width: 80px;
            height: 80px;
            background-color: #f1f1f1;
            border-radius: 16px;
            margin-right: 20px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 30px;
            color: #3a6df0;
        }
        
        .game-title {
            margin: 0 0 5px 0;
            font-size: 24px;
        }
        
        .game-meta {
            color: #666;
            font-size: 14px;
        }
        
        .game-description {
            margin-bottom: 30px;
            line-height: 1.6;
        }
        
        .game-details {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 20px;
            margin-bottom: 30px;
            background-color: #f9f9f9;
            padding: 20px;
            border-radius: 8px;
        }
        
        .detail-item {
            display: flex;
            flex-direction: column;
        }
        
        .detail-label {
            font-size: 14px;
            color: #666;
            margin-bottom: 5px;
        }
        
        .detail-value {
            font-weight: 500;
        }
        
        .download-button {
            background-color: #3a6df0;
            color: white;
            border: none;
            padding: 15px 30px;
            border-radius: 4px;
            font-size: 16px;
            font-weight: 500;
            cursor: pointer;
            display: flex;
            align-items: center;
            justify-content: center;
            transition: background-color 0.3s;
            width: 100%;
            margin-bottom: 20px;
            text-decoration: none;
        }
        
        .download-button:hover {
            background-color: #2a5cd0;
        }
        
        .download-icon {
            margin-right: 10px;
        }
        
        .note {
            background-color: #fff8e1;
            padding: 15px;
            border-left: 4px solid #ffc107;
            margin-bottom: 20px;
            font-size: 14px;
        }
        
        .requirements {
            margin-top: 30px;
        }
        
        .requirements h3 {
            margin-bottom: 15px;
            font-size: 18px;
        }
        
        .requirements ul {
            padding-left: 20px;
        }
        
        .requirements li {
            margin-bottom: 8px;
        }
    </style>
</head>
<body>


<%
    Game game = (Game) request.getAttribute("game");
    User currentUser = (User) session.getAttribute("user");
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
%>

<section class="download-section">
    <div class="container">
        <% if (game != null) { %>
            <div class="download-container">
                <div class="game-header">
                    <div class="game-icon">
                        <i class="fas fa-gamepad"></i>
                    </div>
                    <div>
                        <h1 class="game-title"><%= game.getTitle() %></h1>
                        <div class="game-meta">
                            Version <%= game.getVersion() %> • 
                            Released: <%= dateFormat.format(game.getReleaseDate()) %> • 
                            <%= game.getEsrbRating() %> • 
                            <%= game.getType() %>
                        </div>
                    </div>
                </div>
                
                <div class="game-description">
                    <p><%= game.getDescription() %></p>
                </div>
                
                <div class="game-details">
                    <div class="detail-item">
                        <span class="detail-label">Size</span>
                        <span class="detail-value"><%= game.getSize() %> MB</span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Developer</span>
                        <span class="detail-value">
                            <% if (game.getDeveloper() != null) { %>
                                <%= game.getDeveloper().getName() %>
                            <% } else { %>
                                Developer #<%= game.getGameDeveloperId() %>
                            <% } %>
                        </span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Platform</span>
                        <span class="detail-value">Windows</span>
                    </div>
                </div>
                
                <% if (currentUser != null) { %>
                    <form action="${pageContext.request.contextPath}/games/download" method="post">
                        <input type="hidden" name="gameId" value="<%= game.getGameId() %>">
                        <button type="submit" class="download-button">
                            <span class="download-icon"><i class="fas fa-download"></i></span>
                            Download Game
                        </button>
                    </form>

                    <div class="note">
                        <strong>Note:</strong> By downloading this game, you agree to test it and provide feedback to the developer.
                    </div>
                <% } else { %>
                    <div class="note">
                        <strong>You must be logged in to download this game.</strong> 
                        <a href="${pageContext.request.contextPath}/auth?action=login">Log in</a> or 
                        <a href="${pageContext.request.contextPath}/auth?action=register">register</a> to continue.
                    </div>
                <% } %>
                
                <div class="requirements">
                    <h3>System Requirements</h3>
                    <ul>
                        <li><strong>OS:</strong> Windows 10 (64-bit)</li>
                        <li><strong>Processor:</strong> Intel Core i5 or equivalent</li>
                        <li><strong>Memory:</strong> 8 GB RAM</li>
                        <li><strong>Graphics:</strong> NVIDIA GeForce GTX 960 or equivalent</li>
                        <li><strong>Storage:</strong> <%= game.getSize().intValue() + 1 %> GB available space</li>
                    </ul>
                </div>
            </div>
        <% } else { %>
            <div class="download-container">
                <h2>Game Not Found</h2>
                <p>The requested game could not be found. Please check the game ID and try again.</p>
                <a href="${pageContext.request.contextPath}/games/browse" class="btn">Browse Games</a>
            </div>
        <% } %>
    </div>
</section>


</body>
</html>
