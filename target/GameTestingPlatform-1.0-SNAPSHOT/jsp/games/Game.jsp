<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.pregame.gametesting.model.Game" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<!-- Add page-specific inline styles after header include -->
<style>
    /* Game details specific styling */
    .game-page {
        padding-top: 2rem;
        padding-bottom: 3rem;
        background-color: var(--gray-100);
    }

    .game-details {
        background-color: var(--light);
        border-radius: 8px;
        box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
        overflow: hidden;
        padding: 2rem;
    }

    .game-header {
        padding: 0 0 1.5rem 0;
        border-bottom: 1px solid var(--gray-200);
        margin-bottom: 1.5rem;
    }

    .game-header .row {
        display: flex;
        flex-wrap: wrap;
        gap: 2rem;
    }

    .game-image {
        flex: 0 0 350px;
        position: relative;
        border-radius: 8px;
        overflow: hidden;
        box-shadow: 0 8px 16px rgba(0, 0, 0, 0.12);
        transition: transform 0.3s ease;
    }

    .game-image:hover {
        transform: translateY(-5px);
    }

    .game-image img {
        width: 100%;
        height: 100%;
        object-fit: cover;
        border-radius: 8px;
    }

    .esrb-badge {
        position: absolute;
        bottom: 10px;
        right: 10px;
        padding: 0.25rem 0.75rem;
        border-radius: 4px;
        font-weight: bold;
        font-size: 0.85rem;
        text-transform: uppercase;
        color: white;
        background-color: rgba(0, 0, 0, 0.7);
    }

    .esrb-badge.E {
        background-color: #4caf50;
    }

    .esrb-badge.T {
        background-color: #ff9800;
    }

    .esrb-badge.M {
        background-color: #f44336;
    }

    .game-info {
        flex: 1;
        min-width: 300px;
    }

    .game-title {
        font-size: 2.25rem;
        margin-bottom: 0.5rem;
        color: #1565c0;
        line-height: 1.2;
        position: relative;
        padding-bottom: 0.75rem;
    }

    .game-title::after {
        content: '';
        position: absolute;
        bottom: 0;
        left: 0;
        width: 60px;
        height: 4px;
        background: linear-gradient(90deg, #1976d2, #42a5f5);
        border-radius: 2px;
    }

    p.developer {
        display: flex;
        align-items: center;
        gap: 0.5rem;
        font-size: 1.1rem;
        color: #6c757d;
        margin-bottom: 1.5rem;
        font-weight: 500;
    }

    p.developer i {
        color: #1976d2;
    }

    .game-stats {
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
        gap: 1.25rem;
        margin-bottom: 2rem;
        padding: 1.25rem;
        background-color: #f8f9fa;
        border-radius: 8px;
        border-left: 4px solid #1976d2;
    }

    .stat-item {
        display: flex;
        align-items: center;
        gap: 1rem;
    }

    .stat-icon {
        display: flex;
        align-items: center;
        justify-content: center;
        width: 45px;
        height: 45px;
        border-radius: 50%;
        background-color: #42a5f5;
        color: white;
        font-size: 1.25rem;
    }

    .stat-details {
        display: flex;
        flex-direction: column;
    }

    .stat-label {
        font-size: 0.85rem;
        color: #6c757d;
        text-transform: uppercase;
        letter-spacing: 0.5px;
        font-weight: 600;
    }

    .stat-value {
        font-size: 1.1rem;
        font-weight: 600;
        color: #333333;
    }

    .unreleased {
        color: #ffc107;
        font-weight: 600;
    }

    .game-actions {
        display: flex;
        gap: 1rem;
        margin-top: 1.5rem;
    }

    .game-actions .btn {
        padding: 0.75rem 1.5rem;
        font-weight: 600;
        display: flex;
        align-items: center;
        gap: 0.5rem;
        border-radius: 6px;
        transition: all 0.3s ease;
    }

    .game-actions .btn-primary {
        background: linear-gradient(135deg, #1976d2, #1565c0);
        border: none;
        box-shadow: 0 4px 12px rgba(25, 118, 210, 0.3);
    }

    .game-actions .btn-primary:hover {
        transform: translateY(-3px);
        box-shadow: 0 6px 15px rgba(25, 118, 210, 0.4);
    }

    .game-description {
        margin-top: 2rem;
        padding: 2rem;
        background-color: #f8f9fa;
        border-radius: 8px;
        border-left: 4px solid #1976d2;
    }

    .description-title {
        font-size: 1.5rem;
        margin-bottom: 1rem;
        color: #1565c0;
    }

    .description-content {
        line-height: 1.7;
        color: #333333;
        font-size: 1.05rem;
    }

    /* Responsive design for game details */
    @media (max-width: 768px) {
        .game-header .row {
            flex-direction: column;
        }

        .game-image {
            flex: auto;
            max-width: 100%;
        }

        .game-stats {
            grid-template-columns: 1fr;
        }
    }
</style>

<main class="game-page">
    <div class="container">
        <div class="game-details">
            <div class="game-header">
                <div class="row">
                    <div class="game-image">
                        <img src="https://via.placeholder.com/400x300?text=${game.title}" alt="${game.title} Cover" class="img-fluid">
                        <div class="esrb-badge ${game.esrbRating}">
                            ${game.esrbRating}
                        </div>
                    </div>
                    <div class="game-info">
                        <h1 class="game-title">${game.title}</h1>
                        <p class="developer">
                            <i class="fas fa-user-edit"></i>
                            By <c:choose>
                                <c:when test="${game.developer != null && game.developer.name != null}">
                                    ${game.developer.name}
                                </c:when>
                                <c:otherwise>
                                    Unknown Developer
                                </c:otherwise>
                            </c:choose>
                        </p>

                        <div class="game-stats">
                            <div class="stat-item">
                                <div class="stat-icon">
                                    <i class="fas fa-gamepad"></i>
                                </div>
                                <div class="stat-details">
                                    <span class="stat-label">Type</span>
                                    <span class="stat-value">${game.type}</span>
                                </div>
                            </div>

                            <div class="stat-item">
                                <div class="stat-icon">
                                    <i class="fas fa-code-branch"></i>
                                </div>
                                <div class="stat-details">
                                    <span class="stat-label">Version</span>
                                    <span class="stat-value">${game.version}</span>
                                </div>
                            </div>

                            <div class="stat-item">
                                <div class="stat-icon">
                                    <i class="fas fa-hdd"></i>
                                </div>
                                <div class="stat-details">
                                    <span class="stat-label">Size</span>
                                    <span class="stat-value">${game.size} MB</span>
                                </div>
                            </div>

                            <div class="stat-item">
                                <div class="stat-icon">
                                    <i class="fas fa-calendar-alt"></i>
                                </div>
                                <div class="stat-details">
                                    <span class="stat-label">Release Date</span>
                                    <span class="stat-value">
                                        <c:choose>
                                            <c:when test="${not empty game.releaseDate}">
                                                <fmt:formatDate value="${game.releaseDate}" pattern="MMM dd, yyyy" />
                                            </c:when>
                                            <c:otherwise>
                                                <span class="unreleased">Coming Soon</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </span>
                                </div>
                            </div>
                        </div>

                        <div class="game-actions">
                            <a href="${pageContext.request.contextPath}/games/test?id=${game.gameId}" class="btn btn-primary">
                                <i class="fas fa-vial"></i> Test This Game
                            </a>
                            <a href="${pageContext.request.contextPath}/games/browse" class="btn btn-outline-secondary">
                                <i class="fas fa-arrow-left"></i> Back to Games
                            </a>
                        </div>
                    </div>
                </div>
            </div>

            <div class="game-description">
                <h2 class="description-title">Game Description</h2>
                <div class="description-content">
                    ${game.description}
                </div>
            </div>
        </div>
    </div>
</main>


