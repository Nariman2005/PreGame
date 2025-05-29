<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Upload Game | PreGame Testing</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
  <style>
    .main-container {
      max-width: 800px;
      margin: 30px auto;
      padding: 20px;
      background-color: #f9f9f9;
      border-radius: 8px;
      box-shadow: 0 4px 12px rgba(0,0,0,0.05);
    }
    .page-header {
      text-align: center;
      margin-bottom: 30px;
      color: var(--primary);
    }
    .upload-form {
      background-color: #ffffff;
      padding: 25px;
      border-radius: 8px;
      box-shadow: 0 2px 8px rgba(0,0,0,0.08);
    }
    .form-group {
      margin-bottom: 20px;
    }
    .form-group label {
      display: block;
      margin-bottom: 5px;
      font-weight: 500;
    }
    .form-control {
      width: 100%;
      padding: 10px;
      border: 1px solid #ddd;
      border-radius: 4px;
      font-size: 1rem;
    }
    .form-control:focus {
      border-color: var(--primary);
      outline: none;
      box-shadow: 0 0 0 2px rgba(34, 97, 152, 0.2);
    }
    textarea.form-control {
      min-height: 120px;
    }
    .file-input-group {
      position: relative;
    }
    .file-input-label {
      display: inline-block;
      background-color: var(--primary);
      color: white;
      padding: 10px 15px;
      border-radius: 4px;
      cursor: pointer;
    }
    .file-name-display {
      margin-left: 10px;
      font-style: italic;
    }
    input[type="file"] {
      position: absolute;
      left: 0;
      top: 0;
      opacity: 0;
      width: 0.1px;
      height: 0.1px;
    }
    .btn-submit {
      background-color: var(--primary);
      color: white;
      border: none;
      padding: 12px 25px;
      font-size: 1rem;
      border-radius: 4px;
      cursor: pointer;
      transition: background-color 0.3s;
    }
    .btn-submit:hover {
      background-color: #1a4971;
    }
    .error-message {
      color: #dc3545;
      margin-top: 20px;
    }
    .success-message {
      color: #28a745;
      margin-top: 20px;
    }
    .upload-progress {
      margin-top: 15px;
      width: 100%;
      height: 20px;
      background-color: #f0f0f0;
      border-radius: 10px;
      overflow: hidden;
      display: none;
    }
    .progress-bar {
      height: 100%;
      width: 0%;
      background-color: var(--primary);
      color: white;
      text-align: center;
      line-height: 20px;
      font-size: 12px;
      transition: width 0.3s ease;
    }
  </style>
</head>
<body>

<jsp:include page="../common/header.jsp">
  <jsp:param name="title" value="Upload Game" />
</jsp:include>

<div class="main-container">
  <h1 class="page-header">Upload Your Game</h1>

  <c:if test="${not empty errorMessage}">
    <div class="error-message">
      <i class="fas fa-exclamation-circle"></i> ${errorMessage}
    </div>
  </c:if>

  <c:if test="${not empty successMessage}">
    <div class="success-message">
      <i class="fas fa-check-circle"></i> ${successMessage}
    </div>
  </c:if>

  <div class="upload-form">
    <form action="${pageContext.request.contextPath}/s3upload" method="post" enctype="multipart/form-data" id="uploadForm">
      <div class="form-group">
        <label for="gameTitle">Game Title *</label>
        <input type="text" id="gameTitle" name="gameTitle" class="form-control" required>
      </div>

      <div class="form-group">
        <label for="gameDescription">Game Description *</label>
        <textarea id="gameDescription" name="gameDescription" class="form-control" required></textarea>
      </div>

      <div class="form-group">
        <label for="gameGenre">Game Genre *</label>
        <select id="gameGenre" name="gameGenre" class="form-control" required>
          <option value="">Select genre...</option>
          <option value="Action">Action</option>
          <option value="Adventure">Adventure</option>
          <option value="RPG">RPG</option>
          <option value="Strategy">Strategy</option>
          <option value="Simulation">Simulation</option>
          <option value="Sports">Sports</option>
          <option value="Puzzle">Puzzle</option>
          <option value="Shooter">Shooter</option>
          <option value="Fighting">Fighting</option>
          <option value="Platform">Platform</option>
          <option value="Racing">Racing</option>
          <option value="Other">Other</option>
        </select>
      </div>

      <div class="form-group">
        <label for="gameVersion">Game Version *</label>
        <input type="text" id="gameVersion" name="gameVersion" class="form-control" placeholder="e.g. 1.0.0, Beta 0.5" required>
      </div>

      <div class="form-group">
        <label for="esrbRating">ESRB Rating *</label>
        <select id="esrbRating" name="esrbRating" class="form-control" required>
          <option value="">Select rating...</option>
          <option value="Everyone">Everyone (E)</option>
          <option value="Everyone 10+">Everyone 10+ (E10+)</option>
          <option value="Teen">Teen (T)</option>
          <option value="Mature 17+">Mature 17+ (M)</option>
          <option value="Adults Only 18+">Adults Only 18+ (AO)</option>
          <option value="Rating Pending">Rating Pending (RP)</option>
        </select>
      </div>

      <div class="form-group">
        <label for="gameFile">Game File *</label>
        <div class="file-input-group">
          <label class="file-input-label">
            <i class="fas fa-upload"></i> Choose File
            <input type="file" id="gameFile" name="gameFile" accept=".zip,.rar,.exe,.jar,.html" required>
          </label>
          <span class="file-name-display" id="fileName">No file chosen</span>
        </div>
        <div class="upload-progress" id="uploadProgress">
          <div class="progress-bar" id="progressBar">0%</div>
        </div>
      </div>

      <div class="form-group" style="margin-top: 30px; text-align: center;">
        <button type="submit" class="btn-submit" id="submitBtn">
          <i class="fas fa-cloud-upload-alt"></i> Upload Game to Cloud
        </button>
      </div>
    </form>
  </div>
</div>

<jsp:include page="../common/footer.jsp" />

<script>
  // Display selected file name
  document.getElementById('gameFile').addEventListener('change', function() {
    const fileName = this.files[0] ? this.files[0].name : 'No file chosen';
    document.getElementById('fileName').textContent = fileName;
  });

  // Form submission behavior with progress indicator
  document.getElementById('uploadForm').addEventListener('submit', function(e) {
    const fileInput = document.getElementById('gameFile');
    if (!fileInput.files || fileInput.files.length === 0) {
      e.preventDefault();
      alert('Please select a file to upload');
      return;
    }

    // Show progress bar for visual feedback
    document.getElementById('uploadProgress').style.display = 'block';
    document.getElementById('submitBtn').disabled = true;
    document.getElementById('submitBtn').innerHTML = '<i class="fas fa-spinner fa-spin"></i> Uploading...';

    // Improved progress simulation with completion
    let width = 0;
    const interval = setInterval(function() {
      if (width >= 100) {
        clearInterval(interval);
      } else {
        // Faster initial progress, then slow down
        if (width < 70) {
          width += Math.floor(Math.random() * 10) + 5;
        } else if (width < 90) {
          width += Math.floor(Math.random() * 5) + 1;
        } else {
          width += 1; // Slower at the end to simulate final processing
        }

        if (width > 100) width = 100;

        const progressBar = document.getElementById('progressBar');
        progressBar.style.width = width + '%';
        progressBar.innerHTML = width + '%';

        // When we reach 100%, update the text
        if (width === 100) {
          document.getElementById('submitBtn').innerHTML = '<i class="fas fa-check-circle"></i> Upload Complete!';
        }
      }
    }, 200); // Faster updates for smoother animation
  });
</script>

</body>
</html>
