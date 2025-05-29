<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Games | PreGame Testing</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <style>
        .games-container {
            max-width: 1000px;
            margin: 40px auto;
            padding: 2rem;
        }

        .games-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 2rem;
            padding-bottom: 1rem;
            border-bottom: 2px solid #f0f0f0;
        }

        .games-header h1 {
            color: #3a6df0;
            font-size: 2.2rem;
            margin: 0;
        }

        .upload-btn {
            background-color: #3a6df0;
            color: white;
            border: none;
            padding: 0.8rem 1.5rem;
            border-radius: 8px;
            font-weight: 600;
            display: flex;
            align-items: center;
            gap: 8px;
            text-decoration: none;
            transition: all 0.3s;
        }

        .upload-btn:hover {
            background-color: #2a5ad0;
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }

        .games-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 2rem;
        }

        .game-card {
            background: white;
            border-radius: 12px;
            overflow: hidden;
            box-shadow: 0 4px 12px rgba(0,0,0,0.08);
            transition: all 0.3s;
        }

        .game-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 20px rgba(0,0,0,0.12);
        }

        .game-image {
            height: 180px;
            background-color: #f0f0f0;
            display: flex;
            align-items: center;
            justify-content: center;
            color: #999;
            font-size: 3rem;
        }

        .game-content {
            padding: 1.5rem;
        }

        .game-title {
            font-size: 1.2rem;
            font-weight: 700;
            margin: 0 0 0.5rem;
            color: #333;
        }

        .game-meta {
            color: #777;
            font-size: 0.9rem;
            display: flex;
            align-items: center;
            gap: 8px;
            margin-bottom: 0.7rem;
        }

        .game-meta i {
            color: #3a6df0;
        }

        .game-description {
            color: #555;
            font-size: 0.95rem;
            line-height: 1.5;
            margin-bottom: 1rem;
            display: -webkit-box;
            -webkit-line-clamp: 3;
            -webkit-box-orient: vertical;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        .game-actions {
            display: flex;
            justify-content: space-between;
            margin-top: 1rem;
        }

        .game-btn {
            padding: 0.5rem 1rem;
            font-size: 0.9rem;
            font-weight: 600;
            border-radius: 6px;
            cursor: pointer;
            text-decoration: none;
            transition: all 0.2s;
        }

        .view-btn {
            background-color: #f0f0f0;
            color: #333;
        }

        .view-btn:hover {
            background-color: #e0e0e0;
        }

        .edit-btn {
            background-color: #3a6df0;
            color: white;
        }

        .edit-btn:hover {
            background-color: #2a5ad0;
        }

        .download-btn {
            background-color: #4caf50;
            color: white;
        }

        .download-btn:hover {
            background-color: #45a049;
        }

        .no-games {
            text-align: center;
            padding: 3rem;
            color: #777;
            background-color: #f8f8f8;
            border-radius: 12px;
        }

        .no-games i {
            font-size: 3rem;
            margin-bottom: 1rem;
            color: #aaa;
        }

        .no-games p {
            font-size: 1.2rem;
            margin-bottom: 2rem;
        }
    </style>
</head>
<body>
<!-- Include Header -->


<div class="games-container">
    <div class="games-header">
        <h1><i class="fas fa-gamepad" style="margin-right: 10px;"></i>My Games</h1>
        <a href="${pageContext.request.contextPath}/upload-game" class="upload-btn">
            <i class="fas fa-cloud-upload-alt"></i> Upload New Game
        </a>
    </div>

    <%
        java.util.List<com.pregame.gametesting.model.Game> games =
                (java.util.List<com.pregame.gametesting.model.Game>)request.getAttribute("games");

        if (games == null || games.isEmpty()) {
    %>
    <!-- No games found -->
    <div class="no-games">
        <i class="fas fa-dice"></i>
        <h2>You haven't uploaded any games yet</h2>
        <p>Start sharing your games with our community of testers!</p>
        <a href="${pageContext.request.contextPath}/upload-game" class="upload-btn">
            <i class="fas fa-cloud-upload-alt"></i> Upload Your First Game
        </a>
    </div>
    <%
    } else {
    %>
    <!-- Games grid -->
    <div class="games-grid">
        <%
            for (com.pregame.gametesting.model.Game game : games) {
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMM dd, yyyy");
                String formattedDate = "";
                if (game.getReleaseDate() != null) {
                    formattedDate = sdf.format(game.getReleaseDate());
                }
        %>
        <div class="game-card">
            <div class="game-image">
                <i class="fas fa-gamepad"></i>
            </div>
            <div class="game-content">
                <h3 class="game-title"><%= game.getTitle() %></h3>
                <div class="game-meta">
                    <span><i class="fas fa-calendar"></i> <%= formattedDate %></span>
                </div>
                <div class="game-meta">
                    <span><i class="fas fa-tag"></i> <%= game.getType() %></span>
                    <span><i class="fas fa-certificate"></i> <%= game.getEsrbRating() %></span>
                    <span><i class="fas fa-code-branch"></i> <%= game.getVersion() %></span>
                </div>
                <p class="game-description"><%= game.getDescription() %></p>
                <div class="game-actions">
                    <a href="${pageContext.request.contextPath}/games/view?id=<%= game.getGameId() %>" class="game-btn view-btn">
                        <i class="fas fa-eye"></i> View
                    </a>
                    <a href="${pageContext.request.contextPath}/games/download?id=<%= game.getGameId() %>" class="game-btn download-btn">
                        <i class="fas fa-download"></i> Download
                    </a>
                    <a href="${pageContext.request.contextPath}/games/edit?id=<%= game.getGameId() %>" class="game-btn edit-btn">
                        <i class="fas fa-edit"></i> Edit
                    </a>
                </div>
            </div>
        </div>
        <%
            }
        %>
    </div>
    <%
    }
    %>
</div>

<!-- Include Footer -->
</body>
</html>
