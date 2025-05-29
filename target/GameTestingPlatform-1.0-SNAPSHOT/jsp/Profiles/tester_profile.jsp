<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tester Profile | PreGame Testing</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <!-- Font Awesome for icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <style>
        .profile-container {
            max-width: 900px;
            margin: 40px auto;
            padding: 2rem;
            border-radius: 12px;
            box-shadow: 0 0 20px rgba(58, 109, 240, 0.1);
            background-color: #ffffff;
        }
        .profile-header {
            text-align: center;
            margin-bottom: 2rem;
        }
        .profile-header h2 {
            color: var(--primary);
            margin-bottom: 0.5rem;
        }
        .profile-header .user-type {
            color: var(--gray-600);
            font-size: 1.1rem;
            font-weight: 600;
        }
        .profile-section {
            display: flex;
            gap: 2rem;
            align-items: flex-start;
            margin-bottom: 2rem;
        }
        .profile-pic-container {
            flex: 0 0 180px;
            text-align: center;
        }
        .profile-pic {
            width: 140px;
            height: 140px;
            border-radius: 50%;
            object-fit: cover;
            border: 3px solid var(--primary);
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }
        .change-photo-btn {
            display: inline-block;
            margin-top: 10px;
            padding: 8px 12px;
            background-color: var(--gray-200);
            color: var(--secondary);
            border-radius: 4px;
            font-size: 0.9rem;
            cursor: pointer;
            transition: all 0.3s;
        }
        .change-photo-btn:hover {
            background-color: var(--gray-300);
        }
        .info {
            flex: 1;
        }
        .info-group {
            margin-bottom: 20px;
        }
        .info label {
            font-weight: 600;
            display: block;
            margin-bottom: 8px;
            color: var(--secondary);
        }
        .info input { /* Not used in current display-only fields */
            width: 100%;
            padding: 12px;
            border: 1px solid var(--gray-300);
            border-radius: 6px;
            font-size: 1rem;
            transition: border 0.3s;
        }
        .info input:focus {
            border-color: var(--primary);
            outline: none;
            box-shadow: 0 0 0 3px rgba(58, 109, 240, 0.1);
        }
        .action-buttons { /* This class seems unused in the provided snippet, but good to keep if planned for edit functionality */
            display: flex;
            justify-content: space-between;
            margin-top: 2rem;
        }
        .save-btn {
            background-color: var(--primary);
            color: white;
            padding: 12px 24px;
            border: none;
            border-radius: 6px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s;
        }
        .save-btn:hover {
            background-color: var(--primary-dark);
        }
        .cancel-btn {
            background-color: var(--gray-200);
            color: var(--gray-800);
            padding: 12px 24px;
            border: none;
            border-radius: 6px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s;
        }
        .cancel-btn:hover {
            background-color: var(--gray-300);
        }

        /* Styles for the feature buttons */
        .feature-buttons {
            display: flex;
            justify-content: space-around; /* Changed for better spacing if more buttons are added */
            margin-top: 2rem;
            gap: 1rem; /* Space between buttons */
            flex-wrap: wrap; /* Allow buttons to wrap on smaller screens */
        }

        .feature-btn {
            flex-basis: calc(33.333% - 1rem); /* Adjust for 3 buttons per row, accounting for gap */
            max-width: calc(33.333% - 1rem);
            min-width: 150px; /* Minimum width for smaller screens */
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            background-color: #f8f9fa;
            border: 2px solid var(--gray-300);
            border-radius: 10px;
            padding: 1.5rem 1rem;
            cursor: pointer;
            transition: all 0.3s;
            text-decoration: none;
            color: var(--gray-800);
        }

        .feature-btn:hover {
            border-color: var(--primary);
            transform: translateY(-5px);
            box-shadow: 0 10px 20px rgba(0,0,0,0.1);
        }

        .feature-btn i {
            font-size: 2rem;
            color: var(--primary);
            margin-bottom: 0.5rem;
        }

        .feature-btn span {
            font-weight: 600;
            text-align: center;
        }

        /* Feedback Submission Button styles */
        .feedback-submission-btn { /* Renamed for clarity from upload-game-btn */
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            background-color: #ffffff;
            border: 2px solid var(--primary);
            color: var(--primary);
            border-radius: 12px;
            padding: 1.5rem;
            cursor: pointer;
            transition: all 0.3s ease;
            text-decoration: none;
            margin-top: 1.5rem;
            width: 100%;
        }

        .feedback-submission-btn:hover {
            background-color: var(--primary);
            color: white;
            transform: translateY(-5px);
            box-shadow: 0 10px 20px rgba(0,0,0,0.1);
        }

        .feedback-submission-btn i {
            font-size: 2.5rem;
            margin-bottom: 1rem;
        }

        .feedback-submission-btn span {
            font-size: 1.2rem;
            font-weight: 600;
        }

        .chat-icon {
            position: fixed;
            bottom: 30px;
            right: 30px;
            width: 60px;
            height: 60px;
            border-radius: 50%;
            background-color: var(--primary);
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-size: 1.5rem;
            box-shadow: 0 5px 15px rgba(58, 109, 240, 0.4);
            cursor: pointer;
            transition: all 0.3s;
            z-index: 100;
        }
    </style>
</head>
<body>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Tester Profile" />
</jsp:include>

<div class="profile-container">
    <div class="profile-header">
        <h2>${requestScope.username}</h2>
        <div class="user-type">Tester</div>
    </div>

    <%-- The form seems to be for profile picture upload, but no processing for it is shown.
         If not used for updating profile info, it can be simplified or removed. --%>
    <form method="post" action="${pageContext.request.contextPath}/profile/update-picture" enctype="multipart/form-data">
        <div class="profile-section">
            <div class="profile-pic-container">
                <img src="${pageContext.request.contextPath}/images/default-profile.png" alt="Profile Picture" class="profile-pic">
                <%-- Add an actual file input if photo upload is intended --%>
                <%-- <input type="file" name="profilePicture" id="profilePictureInput" style="display:none;"> --%>
                <label for="profilePictureInput" class="change-photo-btn">
                    <i class="fas fa-camera"></i> Change Photo
                </label>
            </div>

            <div class="info">
                <div class="info-group">
                    <label>Name</label>
                    <p>${requestScope.username}</p> <%-- Displaying as text --%>
                </div>

                <div class="info-group">
                    <label>Email</label>
                    <p>${requestScope.email}</p> <%-- Displaying as text --%>
                </div>
            </div>
        </div>
        <%-- If there's profile info to save, add a submit button here --%>
        <%-- <div class="action-buttons"><button type="submit" class="save-btn">Save Changes</button></div> --%>
    </form>

    <!-- Feature Buttons -->
    <div class="feature-buttons">
        <a href="${pageContext.request.contextPath}/gamer/tested-games" class="feature-btn">
            <i class="fas fa-gamepad"></i>
            <span>My Test Games</span>
        </a>

        <a href="${pageContext.request.contextPath}/gamer/reviews" class="feature-btn">
            <i class="fas fa-star"></i>
            <span>My Reviews</span>
        </a>

        <a href="${pageContext.request.contextPath}/tester/wallet" class="feature-btn">
            <i class="fas fa-wallet"></i>
            <span>My Wallet</span>
        </a>
    </div>

    <!-- Feedback Submission Button -->
    <a href="${pageContext.request.contextPath}/submit-feedback" class="feedback-submission-btn">
        <i class="fas fa-comment-dots"></i>
        <span>Review Submission</span>
    </a>
</div>

<!-- Chat Icon -->
<a href="${pageContext.request.contextPath}/messages" class="chat-icon">
    <i class="fas fa-comment"></i>
</a>

<jsp:include page="../common/footer.jsp" />

</body>
</html>