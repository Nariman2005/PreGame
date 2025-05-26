<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Upload Success - PreGame Testing</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
  <style>
    /* Copy the same CSS from s3upload-test.jsp and add these styles */
    .result-container {
      max-width: 700px;
      margin: 40px auto;
      padding: 20px;
      background-color: #fff;
      border-radius: 10px;
      box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
    }

    .success-message {
      display: flex;
      align-items: center;
      padding: 15px;
      background-color: #e6f7e6;
      border-left: 4px solid #2ecc71;
      margin-bottom: 20px;
      border-radius: 5px;
    }

    .success-icon {
      font-size: 24px;
      color: #2ecc71;
      margin-right: 15px;
    }

    .file-details {
      background-color: #f8f9fa;
      padding: 15px;
      border-radius: 5px;
      margin-bottom: 20px;
    }

    .file-details h3 {
      margin-top: 0;
      color: #333;
    }

    .detail-row {
      display: flex;
      margin-bottom: 10px;
    }

    .detail-label {
      flex: 1;
      font-weight: bold;
      color: #555;
    }

    .detail-value {
      flex: 2;
    }

    .download-link {
      display: inline-block;
      margin-top: 10px;
      padding: 10px 15px;
      background-color: #3a6df0;
      color: white;
      text-decoration: none;
      border-radius: 5px;
      transition: background-color 0.3s;
    }

    .download-link:hover {
      background-color: #2a5cd0;
    }

    .back-btn {
      display: inline-block;
      margin-top: 20px;
      padding: 10px 15px;
      background-color: #6c757d;
      color: white;
      text-decoration: none;
      border-radius: 5px;
      transition: background-color 0.3s;
    }

    .back-btn:hover {
      background-color: #5a6268;
    }
  </style>
</head>
<body>

<header class="header">
  <div class="header-content">
    <div class="logo">
      <a href="${pageContext.request.contextPath}/">PreGame Testing</a>
    </div>
    <nav class="main-nav">
      <ul>
        <li><a href="${pageContext.request.contextPath}/">Home</a></li>
        <li><a href="${pageContext.request.contextPath}/s3upload-test">S3 Upload Test</a></li>
        <li><a href="${pageContext.request.contextPath}/auth?action=login">Login</a></li>
        <li><a href="${pageContext.request.contextPath}/auth?action=register">Register</a></li>
      </ul>
    </nav>
  </div>
</header>

<div class="container">
  <div class="result-container">
    <div class="success-message">
      <div class="success-icon">
        <i class="fas fa-check-circle"></i>
      </div>
      <div>
        <h2>Upload Successful!</h2>
        <p>Your game file has been successfully uploaded to AWS S3.</p>
      </div>
    </div>

    <div class="file-details">
      <h3>Game Details</h3>
      <div class="detail-row">
        <div class="detail-label">Title:</div>
        <div class="detail-value">${gameTitle}</div>
      </div>
      <div class="detail-row">
        <div class="detail-label">Description:</div>
        <div class="detail-value">${gameDescription}</div>
      </div>
      <div class="detail-row">
        <div class="detail-label">Genre:</div>
        <div class="detail-value">${gameGenre}</div>
      </div>
      <div class="detail-row">
        <div class="detail-label">Version:</div>
        <div class="detail-value">${gameVersion}</div>
      </div>
      <div class="detail-row">
        <div class="detail-label">ESRB Rating:</div>
        <div class="detail-value">${esrbRating}</div>
      </div>
      <div class="detail-row">
        <div class="detail-label">File Name:</div>
        <div class="detail-value">${fileName}</div>
      </div>
      <div class="detail-row">
        <div class="detail-label">File Size:</div>
        <div class="detail-value">
          ${fileSize < 1024 * 1024 ? String.format("%.2f KB", fileSize / 1024.0) : String.format("%.2f MB", fileSize / (1024.0 * 1024))}
        </div>
      </div>

      <a href="${downloadUrl}" class="download-link" target="_blank">
        <i class="fas fa-download"></i> View Uploaded File
      </a>
    </div>

    <a href="${pageContext.request.contextPath}/s3upload-test" class="back-btn">
      <i class="fas fa-arrow-left"></i> Upload Another File
    </a>
  </div>
</div>

<footer class="footer">
  <div class="footer-content">
    <p>&copy; <%= java.time.Year.now().getValue() %> PreGame Testing Platform. All rights reserved.</p>
  </div>
</footer>

</body>
</html>
