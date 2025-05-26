<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Submit Feedback</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <style>
        .feedback-container {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
            background: white;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }

        .game-info {
            display: flex;
            margin-bottom: 20px;
            align-items: center;
        }

        .game-info img {
            width: 100px;
            height: 100px;
            object-fit: cover;
            margin-right: 20px;
            border-radius: 5px;
        }

        .rating-input {
            display: flex;
            flex-direction: row-reverse;
            justify-content: flex-end;
        }

        .rating-input input {
            display: none;
        }

        .rating-input label {
            font-size: 30px;
            color: #ddd;
            cursor: pointer;
            padding: 5px;
        }

        .rating-input label:hover,
        .rating-input label:hover ~ label,
        .rating-input input:checked ~ label {
            color: #ffcc00;
        }

        .feedback-form textarea {
            width: 100%;
            min-height: 150px;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ddd;
            border-radius: 5px;
        }

        .submit-btn {
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
        }

        .submit-btn:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Submit Feedback" />
</jsp:include>

<div class="main-content">
    <div class="feedback-container">
        <h1>Submit Feedback for Game</h1>

        <div class="game-info">
            <c:if test="${not empty game}">
                <img src="https://via.placeholder.com/100x100?text=${game.title}" alt="${game.title} Cover" />
                <div>
                    <h2>${game.title}</h2>
                    <p>Developer: ${game.developer.name != null ? game.developer.name : 'Unknown Developer'}</p>
                    <p>Type: ${game.type}</p>
                </div>
            </c:if>
            <c:if test="${empty game}">
                <p>Game information not available</p>
            </c:if>
        </div>

        <form action="${pageContext.request.contextPath}/feedback/submit" method="post">
            <input type="hidden" name="gameId" value="${gameId}">

            <div>
                <h3>Rate this game:</h3>
                <div class="rating-input">
                    <input type="radio" id="star5" name="rating" value="5" />
                    <label for="star5" title="5 stars"><i class="fas fa-star"></i></label>

                    <input type="radio" id="star4" name="rating" value="4" />
                    <label for="star4" title="4 stars"><i class="fas fa-star"></i></label>

                    <input type="radio" id="star3" name="rating" value="3" />
                    <label for="star3" title="3 stars"><i class="fas fa-star"></i></label>

                    <input type="radio" id="star2" name="rating" value="2" />
                    <label for="star2" title="2 stars"><i class="fas fa-star"></i></label>

                    <input type="radio" id="star1" name="rating" value="1" />
                    <label for="star1" title="1 star"><i class="fas fa-star"></i></label>
                </div>
            </div>

            <div>
                <h3>Your Feedback:</h3>
                <textarea name="feedbackText" placeholder="Share your experience with this game..." required></textarea>
            </div>

            <button type="submit" class="submit-btn">
                <i class="fas fa-paper-plane"></i> Submit Feedback
            </button>
        </form>
    </div>
</div>

<jsp:include page="/jsp/common/footer.jsp" />

<script>
    document.addEventListener('DOMContentLoaded', function() {
        // Pre-select middle rating (3 stars) by default
        document.getElementById('star3').checked = true;
    });
</script>
</body>
</html>