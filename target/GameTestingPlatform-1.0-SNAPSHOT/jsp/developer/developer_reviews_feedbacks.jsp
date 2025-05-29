<%-- webapp/jsp/developer/developer_feedback.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Feedback for Developer: <c:out value="${developerName}" /></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            background-color: #f8f9fa;
            color: #333;
        }
        .main-content {
            padding: 30px;
            max-width: 1200px;
            margin: 0 auto;
        }
        h1 {
            color: #3a6df0;
            margin-bottom: 30px;
            font-weight: 600;
            font-size: 28px;
        }
        .page-description {
            color: #666;
            margin-bottom: 25px;
            font-size: 16px;
            line-height: 1.6;
        }
        .feedback-container {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(380px, 1fr));
            gap: 24px;
            margin-bottom: 30px;
        }
        .feedback-card {
            background-color: #fff;
            border-radius: 12px;
            padding: 24px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.06);
            display: flex;
            flex-direction: column;
            transition: transform 0.2s ease, box-shadow 0.2s ease;
        }
        .feedback-card:hover {
            transform: translateY(-3px);
            box-shadow: 0 6px 16px rgba(0,0,0,0.1);
        }
        .feedback-card h3 {
            margin-top: 0;
            color: #3a6df0;
            border-bottom: 1px solid #eee;
            padding-bottom: 12px;
            margin-bottom: 16px;
            font-size: 18px;
            display: flex;
            align-items: center;
        }
        .feedback-card h3 i {
            margin-right: 8px;
        }
        .feedback-card p, .feedback-card div {
            margin: 10px 0;
            line-height: 1.6;
        }
        .label {
            font-weight: 500;
            color: #555;
            min-width: 130px;
            display: inline-block;
        }
        .content-item {
            display: flex;
            align-items: baseline;
            margin-bottom: 12px;
        }
        .content-item i {
            color: #666;
            width: 20px;
            text-align: center;
            margin-right: 8px;
        }
        .feedback-text-content {
            background-color: #f9f9f9;
            padding: 15px;
            border-radius: 8px;
            border: 1px solid #eee;
            margin-top: 8px;
            white-space: pre-wrap;
            max-height: 200px;
            overflow-y: auto;
            font-size: 14px;
            color: #444;
        }
        .date {
            font-size: 13px;
            color: #888;
            text-align: right;
            margin-top: auto;
            padding-top: 15px;
            border-top: 1px solid #eee;
        }
        .status {
            padding: 5px 12px;
            border-radius: 20px;
            color: white;
            font-size: 12px;
            display: inline-block;
            font-weight: 500;
        }
        .status-New { background-color: #3498db; }
        .status-Acknowledged { background-color: #f1c40f; }
        .status-InProgress, .status-In_Progress { background-color: #e67e22; }
        .status-Resolved { background-color: #2ecc71; }
        .status-Closed { background-color: #95a5a6; }
        .no-feedback {
            color: #777;
            font-style: italic;
            padding: 25px;
            background-color: #fff;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.06);
            text-align: center;
            font-size: 16px;
        }
        .attachment-info {
            font-size: 13px;
            color: #888;
            display: flex;
            align-items: center;
        }
        .attachment-info i {
            margin-right: 5px;
        }
        .navigation-links {
            margin-top: 30px;
            display: flex;
            gap: 20px;
        }
        .navigation-links a {
            display: inline-flex;
            align-items: center;
            padding: 10px 20px;
            background-color: #3a6df0;
            color: white;
            text-decoration: none;
            border-radius: 8px;
            font-weight: 500;
            transition: background-color 0.2s;
        }
        .navigation-links a i {
            margin-right: 8px;
        }
        .navigation-links a:hover {
            background-color: #2a5ad0;
        }
        .tester-name {
            font-weight: 500;
            color: #444;
        }
    </style>
</head>
<body>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Feedback & Reviews" />
</jsp:include>

<div class="main-content">
    <h1><i class="fas fa-comments"></i> Feedback for Developer: <c:out value="${developerName}" /></h1>
    <p class="page-description">
        Below are all the reviews and feedback for your games submitted by testers. Use this information to improve your games.
    </p>

    <c:choose>
        <c:when test="${not empty feedbackList}">
            <div class="feedback-container">
                <c:forEach var="feedback" items="${feedbackList}">
                    <div class="feedback-card">
                        <h3><i class="fas fa-gamepad"></i> <c:out value="${feedback.gameTitle}" /></h3>

                        <div class="content-item">
                            <i class="fas fa-tag"></i>
                            <span class="label">Feedback Type:</span> <c:out value="${feedback.feedbackType}" />
                        </div>

                        <div class="content-item">
                            <i class="fas fa-info-circle"></i>
                            <span class="label">Status:</span>
                            <span class="status status-${feedback.status}"><c:out value="${feedback.status}" /></span>
                        </div>

                        <div class="content-item">
                            <i class="fas fa-user-check"></i>
                            <span class="label">Tester:</span>
                            <span class="tester-name"><c:out value="${feedback.testerIdentifier}" /></span>
                        </div>

                        <div>
                            <div class="content-item">
                                <i class="fas fa-comment-alt"></i>
                                <span class="label">Feedback Details:</span>
                            </div>
                            <div class="feedback-text-content"><c:out value="${feedback.feedbackText}" /></div>
                        </div>

                        <c:if test="${not empty feedback.attachments}">
                            <p class="attachment-info content-item">
                                <i class="fas fa-paperclip"></i>
                                <span class="label">Attachment:</span>
                                Present (<c:out value="${feedback.attachments.length}" /> bytes)
                                <%-- For actual download, you'd need a separate servlet --%>
                                <%-- <a href="${pageContext.request.contextPath}/downloadAttachment?reviewId=${feedback.reviewId}">Download</a> --%>
                            </p>
                        </c:if>

                        <p class="date">
                            <i class="far fa-calendar-alt"></i>
                            Reviewed on: <fmt:formatDate value="${feedback.reviewDate}" pattern="MMM dd, yyyy" />
                        </p>
                    </div>
                </c:forEach>
            </div>
        </c:when>
        <c:otherwise>
            <p class="no-feedback"><i class="far fa-comment-dots"></i> No feedback found for this developer's games yet.</p>
        </c:otherwise>
    </c:choose>

    <div class="navigation-links">
        <a href="${pageContext.request.contextPath}/developer/games"><i class="fas fa-gamepad"></i> Back to My Games</a>
        <a href="${pageContext.request.contextPath}/dashboard"><i class="fas fa-home"></i> Back to Dashboard</a>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />
</body>
</html>
