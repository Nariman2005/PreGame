<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard - Feedback Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
            padding-top: 20px;
        }
        .sidebar {
            background-color: #343a40;
            color: white;
            height: 100vh;
            position: fixed;
            padding-top: 20px;
        }
        .sidebar a {
            color: white;
            padding: 10px 15px;
            text-decoration: none;
            display: block;
        }
        .sidebar a:hover {
            background-color: #495057;
        }
        .main-content {
            margin-left: 250px;
            padding: 20px;
        }
        .feedback-card {
            margin-bottom: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        .rating-badge {
            font-size: 0.9rem;
            padding: 5px 10px;
        }
        .feedback-header {
            border-bottom: 1px solid #eee;
            padding-bottom: 10px;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <!-- Sidebar -->
        <div class="col-md-3 col-lg-2 d-md-block sidebar">
            <div class="text-center mb-4">
                <h4>Admin Dashboard</h4>
            </div>
            <ul class="nav flex-column">
                <li class="nav-item">
                    <a class="nav-link active" href="#">
                        <i class="bi bi-speedometer2"></i> Dashboard
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/feedback/list">
                        <i class="bi bi-chat-square-text"></i> Feedback Management
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">
                        <i class="bi bi-people"></i> User Management
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/auth/logout">
                        <i class="bi bi-box-arrow-right"></i> Logout
                    </a>
                </li>
            </ul>
        </div>

        <!-- Main content -->
        <div class="col-md-9 ms-sm-auto col-lg-10 px-md-4 main-content">
            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2">Feedback Management</h1>
                <div class="btn-toolbar mb-2 mb-md-0">
                    <div class="btn-group me-2">
                        <button type="button" class="btn btn-sm btn-outline-secondary">Export</button>
                    </div>
                </div>
            </div>

            <!-- Feedback Filters -->
            <div class="card mb-4">
                <div class="card-body">
                    <form class="row g-3">
                        <div class="col-md-3">
                            <label for="gameFilter" class="form-label">Game</label>
                            <select id="gameFilter" class="form-select">
                                <option selected>All Games</option>
                                <!-- Populate with games from database -->
                                <option value="1">Game 1</option>
                                <option value="2">Game 2</option>
                            </select>
                        </div>
                        <div class="col-md-3">
                            <label for="userFilter" class="form-label">User Type</label>
                            <select id="userFilter" class="form-select">
                                <option selected>All Users</option>
                                <option value="gamer">Gamer</option>
                                <option value="developer">Developer</option>
                                <option value="tester">Tester</option>
                            </select>
                        </div>
                        <div class="col-md-3">
                            <label for="ratingFilter" class="form-label">Rating</label>
                            <select id="ratingFilter" class="form-select">
                                <option selected>All Ratings</option>
                                <option value="1">1 Star</option>
                                <option value="2">2 Stars</option>
                                <option value="3">3 Stars</option>
                                <option value="4">4 Stars</option>
                                <option value="5">5 Stars</option>
                            </select>
                        </div>
                        <div class="col-md-3 d-flex align-items-end">
                            <button type="submit" class="btn btn-primary">Filter</button>
                        </div>
                    </form>
                </div>
            </div>

            <!-- Feedback List -->
            <div class="card">
                <div class="card-header">
                    <h5 class="card-title">All Feedback</h5>
                </div>
                <div class="card-body">
                    <c:choose>
                        <c:when test="${not empty feedbacks}">
                            <c:forEach var="feedback" items="${feedbacks}">
                                <div class="card feedback-card">
                                    <div class="card-body">
                                        <div class="d-flex justify-content-between feedback-header">
                                            <div>
                                                <h5 class="card-title">Game ID: ${feedback.gameId}</h5>
                                                <h6 class="card-subtitle mb-2 text-muted">
                                                    From Gamer ID: ${feedback.gamerId}
                                                </h6>
                                            </div>
                                            <div>
                                                    <span class="badge bg-primary rating-badge">
                                                        Rating: ${feedback.rating}/5
                                                    </span>
                                            </div>
                                        </div>
                                        <p class="card-text">${feedback.feedbackText}</p>
                                        <div class="d-flex justify-content-between">
                                            <small class="text-muted">
                                                Submitted on: ${feedback.feedbackDate}
                                            </small>
                                            <div>
                                                <a href="${pageContext.request.contextPath}/feedback/view?id=${feedback.feedbackId}"
                                                   class="btn btn-sm btn-info">View Details</a>
                                                <button class="btn btn-sm btn-danger">Delete</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <div class="alert alert-info" role="alert">
                                No feedback found.
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Add any necessary JavaScript here
    document.addEventListener('DOMContentLoaded', function() {
        // You can add interactive features here
        console.log('Admin dashboard loaded');
    });
</script>
</body>
</html>