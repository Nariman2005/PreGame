<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Browsing Games</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <!-- Font Awesome for icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Browsing Game" />
</jsp:include>
<div class="main-content">
    <h1 class="text-center">Browse Games</h1>

    <div class="filter-form">
        <form action="${pageContext.request.contextPath}/games/browse" method="get">
            <select name="type">
                <option value="">All Types</option>
                <option value="Action" ${selectedType == 'Action' ? 'selected' : ''}>Action</option>
                <option value="Adventure" ${selectedType == 'Adventure' ? 'selected' : ''}>Adventure</option>
                <option value="Puzzle" ${selectedType == 'Puzzle' ? 'selected' : ''}>Puzzle</option>
                <option value="Strategy" ${selectedType == 'Strategy' ? 'selected' : ''}>Strategy</option>
                <option value="RPG" ${selectedType == 'RPG' ? 'selected' : ''}>RPG</option>
            </select>
            <select name="esrbRating">
                <option value="">All ESRB Ratings</option>
                <option value="E" ${selectedEsrb == 'E' ? 'selected' : ''}>Everyone (E)</option>
                <option value="E10+" ${selectedEsrb == 'E10+' ? 'selected' : ''}>Everyone 10+ (E10+)</option>
                <option value="T" ${selectedEsrb == 'T' ? 'selected' : ''}>Teen (T)</option>
                <option value="M" ${selectedEsrb == 'M' ? 'selected' : ''}>Mature (M)</option>
            </select>
            <button type="submit"><i class="fas fa-filter"></i> Filter</button>
        </form>
    </div>

    <div class="game-grid">
        <c:forEach var="game" items="${games}">
            <div class="game-card">
                <img src="https://via.placeholder.com/250x150?text=${game.title}" alt="${game.title} Cover" />
                <div class="content-wrapper">
                    <h3>${game.title}</h3>
                    <p><strong><i class="fas fa-user-edit"></i> By ${game.developer.name != null ? game.developer.name : 'Unknown Developer'}</strong></p>
                    <p><strong><i class="fas fa-gamepad"></i> Type:</strong> ${game.type}</p>
                    <p><strong><i class="fas fa-certificate"></i> ESRB Rating:</strong> ${game.esrbRating}</p>
                    <p><strong><i class="fas fa-code-branch"></i> Version:</strong> ${game.version}</p>

                    <!-- Test Game button -->
                    <a href="${pageContext.request.contextPath}/games/test?id=${game.gameId}" class="test-button">
                        <i class="fas fa-vial"></i> Test Game
                    </a>
                </div>
            </div>
        </c:forEach>

        <c:if test="${empty games}">
            <div class="no-games-message">
                <div class="icon"><i class="fas fa-gamepad"></i></div>
                <p>No games available at the moment. Check back later!</p>
            </div>
        </c:if>
    </div>
</div>


<jsp:include page="/jsp/common/footer.jsp" />

</body>
</html>
