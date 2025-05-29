<%-- webapp/jsp/developer/developer_feedback.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Feedback for Developer: <c:out value="${developerName}" /></title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f4; color: #333; }
        h1 { color: #2c3e50; }
        .feedback-container {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(350px, 1fr)); /* Adjusted minmax */
            gap: 20px;
        }
        .feedback-card {
            background-color: #fff;
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 20px; /* Increased padding */
            box-shadow: 0 2px 5px rgba(0,0,0,0.08);
            display: flex;
            flex-direction: column;
        }
        .feedback-card h3 { margin-top: 0; color: #3498db; border-bottom: 1px solid #eee; padding-bottom: 10px; margin-bottom: 10px; font-size: 1.2em;}
        .feedback-card p, .feedback-card div { margin: 8px 0; line-height: 1.6; }
        .label { font-weight: bold; color: #555; min-width:120px; display: inline-block;}
        .content-item { display: flex; align-items: baseline; }
        .content-item .label { margin-right: 5px;}
        .feedback-text-content {
            background-color: #f9f9f9;
            padding: 10px;
            border-radius: 4px;
            border: 1px solid #eee;
            margin-top: 5px;
            white-space: pre-wrap; /* To respect newlines in feedback */
            max-height: 200px; /* Limit height and make scrollable if too long */
            overflow-y: auto;
        }
        .date { font-size: 0.9em; color: #7f8c8d; text-align: right; margin-top: auto; padding-top:10px; border-top:1px solid #eee;}
        .status { padding: 4px 10px; border-radius: 15px; color: white; font-size: 0.85em; display: inline-block; }
        .status-New { background-color: #3498db; } /* Blue */
        .status-Acknowledged { background-color: #f1c40f; } /* Yellow */
        .status-InProgress, .status-In_Progress { background-color: #e67e22; } /* Orange */
        .status-Resolved { background-color: #2ecc71; } /* Green */
        .status-Closed { background-color: #95a5a6; } /* Grey */
        .no-feedback { color: #777; font-style: italic; padding: 20px; background-color: #fff; border: 1px solid #ddd; border-radius: 8px;}
        .attachment-info { font-size: 0.85em; color: #888; }
        hr { border: 0; border-top: 1px solid #ccc; margin: 25px 0; }
        .page-header { margin-bottom: 20px; }
        .page-header p { font-size: 0.9em; color: #666; }
    </style>
</head>
<body>
<div class="page-header">
    <h1>Feedback for Developer: <c:out value="${developerName}" /></h1>
    <p>(Developer ID: <c:out value="${requestedDeveloperId}" />)</p>
</div>
<hr/>

<c:choose>
    <c:when test="${not empty feedbackList}">
        <div class="feedback-container">
            <c:forEach var="feedback" items="${feedbackList}">
                <div class="feedback-card">
                    <h3>Game: <c:out value="${feedback.gameTitle}" /></h3>

                    <div class="content-item"><span class="label">Feedback Type:</span> <c:out value="${feedback.feedbackType}" /></div>
                    <div class="content-item"><span class="label">Status:</span>
                        <span class="status status-${feedback.status}"><c:out value="${feedback.status}" /></span>
                    </div>
                    <div class="content-item"><span class="label">Tester:</span> <c:out value="${feedback.testerIdentifier}" /></div>

                    <div>
                        <span class="label">Feedback Details:</span>
                        <div class="feedback-text-content"><c:out value="${feedback.feedbackText}" /></div>
                    </div>

                    <c:if test="${not empty feedback.attachments}">
                        <p class="attachment-info content-item">
                            <span class="label">Attachment:</span>
                            Present (<c:out value="${feedback.attachments.length}" /> bytes)
                                <%-- For actual download, you'd need a separate servlet --%>
                                <%-- <a href="${pageContext.request.contextPath}/downloadAttachment?reviewId=${feedback.reviewId}">Download</a> --%>
                        </p>
                    </c:if>
                    <p class="date">
                        Reviewed on: <fmt:formatDate value="${feedback.reviewDate}" pattern="MMM dd, yyyy" />
                    </p>
                </div>
            </c:forEach>
        </div>
    </c:when>
    <c:otherwise>
        <p class="no-feedback">No feedback found for this developer's games yet.</p>
    </c:otherwise>
</c:choose>

<br/>
<p><a href="${pageContext.request.contextPath}/developer/games">Back to My Games</a></p>
<p><a href="${pageContext.request.contextPath}/dashboard">Back to Dashboard</a></p>

</body>
</html>