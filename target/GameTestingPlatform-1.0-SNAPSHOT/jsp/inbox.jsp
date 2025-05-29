<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.pregame.gametesting.model.Feedback" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Feedback Messages | PreGame Testing Platform</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .message-container {
            max-width: 1000px;
            margin: 20px auto;
            padding: 20px;
        }
        .tab-buttons {
            display: flex;
            margin-bottom: 20px;
            border-bottom: 1px solid #ddd;
        }
        .tab-button {
            padding: 10px 20px;
            background: none;
            border: none;
            border-bottom: 2px solid transparent;
            cursor: pointer;
            font-weight: bold;
            font-size: 16px;
        }
        .tab-button.active {
            border-bottom: 2px solid #4c5fd5;
            color: #4c5fd5;
        }
        .tab-content {
            display: none;
        }
        .tab-content.active {
            display: block;
        }
        .message-card {
            background-color: #f8f9fa;
            border-radius: 8px;
            padding: 20px;
            margin-bottom: 20px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }
        .message-header {
            display: flex;
            justify-content: space-between;
            margin-bottom: 10px;
            border-bottom: 1px solid #ddd;
            padding-bottom: 10px;
        }
        .message-date {
            color: #666;
            font-size: 0.9rem;
        }
        .message-body {
            margin-top: 15px;
        }
        .message-attachment {
            margin-top: 15px;
            padding: 10px;
            background-color: #efefef;
            border-radius: 4px;
        }
        .new-feedback-btn {
            background-color: #4c5fd5;
            color: white;
            border: none;
            border-radius: 5px;
            padding: 12px 24px;
            font-size: 16px;
            cursor: pointer;
            margin-bottom: 20px;
        }
        .new-feedback-btn:hover {
            background-color: #3a4cb1;
        }
        .no-messages {
            padding: 20px;
            text-align: center;
            color: #666;
        }
        .alert {
            padding: 15px;
            margin-bottom: 20px;
            border: 1px solid transparent;
            border-radius: 4px;
        }
        .alert-success {
            color: #3c763d;
            background-color: #dff0d8;
            border-color: #d6e9c6;
        }
        .alert-warning {
            color: #8a6d3b;
            background-color: #fcf8e3;
            border-color: #faebcc;
        }
    </style>
</head>
<body>


<div class="message-container">
    <h1>Feedback Messages</h1>

    <% if (request.getParameter("success") != null) { %>
        <div class="alert alert-success">
            Feedback has been sent successfully and delivered to the recipient's email!
        </div>
    <% } else if (request.getParameter("partial") != null) { %>
        <div class="alert alert-warning">
            Feedback has been saved, but there was an issue sending the email to the recipient.
        </div>
    <% } %>

    <button class="new-feedback-btn" onclick="openNewFeedbackModal()">
        <i class="fas fa-plus"></i> Create New Feedback
    </button>

    <div class="tab-buttons">
        <button class="tab-button active" onclick="openTab(event, 'inbox')">Received Messages</button>
        <button class="tab-button" onclick="openTab(event, 'sent')">Sent Messages</button>
    </div>

    <div id="inbox" class="tab-content active">
        <h2>Received Feedback</h2>
        <%
            List<Feedback> receivedFeedbacks = (List<Feedback>)request.getAttribute("receivedFeedbacks");
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");

            if (receivedFeedbacks != null && !receivedFeedbacks.isEmpty()) {
                for (Feedback feedback : receivedFeedbacks) {
        %>
                <div class="message-card">
                    <div class="message-header">
                        <div>
                            <strong>From Gamer ID:</strong> <%= feedback.getGamerId() %>
                            <br>
                            <strong>Game ID:</strong> <%= feedback.getGameId() %>
                            <% if (feedback.getEmail() != null && !feedback.getEmail().isEmpty()) { %>
                                <br>
                                <strong>Email:</strong> <%= feedback.getEmail() %>
                            <% } %>
                        </div>
                        <div class="message-date">
                            <%= dateFormat.format(feedback.getFeedbackDate()) %>
                        </div>
                    </div>
                    <div class="message-body">
                        <%= feedback.getFeedbackText() %>
                    </div>
                    <% if (feedback.getAttachments() != null && feedback.getAttachments().length > 0) { %>
                        <div class="message-attachment">
                            <a href="${pageContext.request.contextPath}/downloadAttachment?feedbackId=<%= feedback.getFeedbackId() %>">
                                Download Attachment
                            </a>
                        </div>
                    <% } %>
                </div>
        <%
                }
            } else {
        %>
                <div class="no-messages">
                    <p>You have no received feedback messages.</p>
                </div>
        <%
            }
        %>
    </div>

    <div id="sent" class="tab-content">
        <h2>Sent Feedback</h2>
        <%
            List<Feedback> sentFeedbacks = (List<Feedback>)request.getAttribute("sentFeedbacks");

            if (sentFeedbacks != null && !sentFeedbacks.isEmpty()) {
                for (Feedback feedback : sentFeedbacks) {
        %>
                <div class="message-card">
                    <div class="message-header">
                        <div>
                            <strong>To Game ID:</strong> <%= feedback.getGameId() %>
                        </div>
                        <div class="message-date">
                            <%= dateFormat.format(feedback.getFeedbackDate()) %>
                        </div>
                    </div>
                    <div class="message-body">
                        <%= feedback.getFeedbackText() %>
                    </div>
                    <% if (feedback.getAttachments() != null && feedback.getAttachments().length > 0) { %>
                        <div class="message-attachment">
                            <a href="${pageContext.request.contextPath}/downloadAttachment?feedbackId=<%= feedback.getFeedbackId() %>">
                                View Attachment
                            </a>
                        </div>
                    <% } %>
                </div>
        <%
                }
            } else {
        %>
                <div class="no-messages">
                    <p>You have not sent any feedback messages.</p>
                </div>
        <%
            }
        %>
    </div>

    <!-- Modal for New Feedback -->
    <div id="feedbackModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeModal()">&times;</span>
            <h2>Send New Feedback</h2>
            <form action="${pageContext.request.contextPath}/submitFeedback" method="post" enctype="multipart/form-data">
                <input type="hidden" name="action" value="submit">
                <input type="hidden" name="gamerId" value="${sessionScope.userId}">

                <div class="form-group">
                    <label for="gameId">Select Game:</label>
                    <select id="gameId" name="gameId" class="form-control" required>
                        <option value="">-- Select a game --</option>
                        <!-- This would ideally be populated from the database -->
                        <option value="1">Space Adventure</option>
                        <option value="2">Fantasy Quest</option>
                        <option value="3">Racing Simulation 2023</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="email">Recipient's Email:</label>
                    <input type="email" id="email" name="email" class="form-control" required placeholder="Enter recipient's email address">
                </div>

                <div class="form-group">
                    <label for="feedbackText">Feedback:</label>
                    <textarea id="feedbackText" name="feedbackText" class="form-control" rows="5" required></textarea>
                </div>

                <div class="form-group">
                    <label for="attachments">Attachments:</label>
                    <input type="file" id="attachments" name="attachments" class="form-control">
                </div>

                <button type="submit" class="new-feedback-btn">Send Feedback</button>
            </form>
        </div>
    </div>

    <script>
        function openTab(evt, tabName) {
            var i, tabcontent, tablinks;
            tabcontent = document.getElementsByClassName("tab-content");
            for (i = 0; i < tabcontent.length; i++) {
                tabcontent[i].style.display = "none";
            }
            tablinks = document.getElementsByClassName("tab-button");
            for (i = 0; i < tablinks.length; i++) {
                tablinks[i].className = tablinks[i].className.replace(" active", "");
            }
            document.getElementById(tabName).style.display = "block";
            evt.currentTarget.className += " active";
        }

        function openNewFeedbackModal() {
            document.getElementById('feedbackModal').style.display = 'block';
        }

        function closeModal() {
            document.getElementById('feedbackModal').style.display = 'none';
        }
    </script>
</body>
</html>
