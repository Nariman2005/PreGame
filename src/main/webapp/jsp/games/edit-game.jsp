<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Game | PreGame Testing</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <style>
        .edit-game-container {
            max-width: 800px;
            margin: 40px auto;
            padding: 2rem;
            background: white;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.08);
        }

        .edit-game-header {
            display: flex;
            align-items: center;
            margin-bottom: 2rem;
            padding-bottom: 1rem;
            border-bottom: 2px solid #f0f0f0;
        }

        .edit-game-header h1 {
            color: #3a6df0;
            font-size: 2.2rem;
            margin: 0;
        }

        .edit-game-header i {
            margin-right: 15px;
            font-size: 1.8rem;
            color: #3a6df0;
        }

        .form-group {
            margin-bottom: 1.5rem;
        }

        .form-group label {
            display: block;
            margin-bottom: 0.5rem;
            font-weight: 600;
            color: #444;
        }

        .form-control {
            width: 100%;
            padding: 0.8rem;
            border: 1px solid #ddd;
            border-radius: 6px;
            font-size: 1rem;
            transition: border-color 0.2s;
        }

        .form-control:focus {
            border-color: #3a6df0;
            outline: none;
        }

        textarea.form-control {
            min-height: 150px;
            resize: vertical;
        }

        .form-actions {
            display: flex;
            justify-content: space-between;
            margin-top: 2rem;
        }

        .btn {
            padding: 0.8rem 1.5rem;
            border: none;
            border-radius: 8px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s;
            display: inline-flex;
            align-items: center;
            gap: 8px;
        }

        .btn-primary {
            background-color: #3a6df0;
            color: white;
        }

        .btn-primary:hover {
            background-color: #2a5ad0;
            transform: translateY(-2px);
        }

        .btn-secondary {
            background-color: #f0f0f0;
            color: #333;
        }

        .btn-secondary:hover {
            background-color: #e0e0e0;
            transform: translateY(-2px);
        }

        .form-note {
            margin-top: 1.5rem;
            padding: 1rem;
            background-color: #f8f8f8;
            border-left: 4px solid #3a6df0;
            color: #555;
        }
    </style>
</head>
<body>
<!-- Include Header -->
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Edit Game" />
</jsp:include>

<div class="edit-game-container">
    <div class="edit-game-header">
        <i class="fas fa-edit"></i>
        <h1>Edit Game: ${game.title}</h1>
    </div>

    <form action="${pageContext.request.contextPath}/games/edit" method="post">
        <input type="hidden" name="gameId" value="${game.gameId}">

        <div class="form-group">
            <label for="title">Game Title</label>
            <input type="text" id="title" name="title" class="form-control" value="${game.title}" required>
        </div>

        <div class="form-group">
            <label for="description">Game Description</label>
            <textarea id="description" name="description" class="form-control" required>${game.description}</textarea>
        </div>

        <div class="form-group">
            <label for="type">Game Type</label>
            <select id="type" name="type" class="form-control" required>
                <option value="Action" ${game.type == 'Action' ? 'selected' : ''}>Action</option>
                <option value="Adventure" ${game.type == 'Adventure' ? 'selected' : ''}>Adventure</option>
                <option value="Puzzle" ${game.type == 'Puzzle' ? 'selected' : ''}>Puzzle</option>
                <option value="RPG" ${game.type == 'RPG' ? 'selected' : ''}>RPG</option>
                <option value="Simulation" ${game.type == 'Simulation' ? 'selected' : ''}>Simulation</option>
                <option value="Strategy" ${game.type == 'Strategy' ? 'selected' : ''}>Strategy</option>
                <option value="Sports" ${game.type == 'Sports' ? 'selected' : ''}>Sports</option>
                <option value="Other" ${game.type == 'Other' ? 'selected' : ''}>Other</option>
            </select>
        </div>

        <div class="form-group">
            <label for="version">Version</label>
            <input type="text" id="version" name="version" class="form-control" value="${game.version}" required>
        </div>

        <div class="form-group">
            <label for="esrbRating">ESRB Rating</label>
            <select id="esrbRating" name="esrbRating" class="form-control" required>
                <option value="E" ${game.esrbRating == 'E' ? 'selected' : ''}>Everyone (E)</option>
                <option value="E10+" ${game.esrbRating == 'E10+' ? 'selected' : ''}>Everyone 10+ (E10+)</option>
                <option value="T" ${game.esrbRating == 'T' ? 'selected' : ''}>Teen (T)</option>
                <option value="M" ${game.esrbRating == 'M' ? 'selected' : ''}>Mature (M)</option>
                <option value="AO" ${game.esrbRating == 'AO' ? 'selected' : ''}>Adults Only (AO)</option>
                <option value="RP" ${game.esrbRating == 'RP' ? 'selected' : ''}>Rating Pending (RP)</option>
            </select>
        </div>

        <div class="form-note">
            <p><i class="fas fa-info-circle"></i> Note: To upload a new version of your game file, please use the upload page.</p>
        </div>

        <div class="form-actions">
            <a href="${pageContext.request.contextPath}/developer/my-games" class="btn btn-secondary">
                <i class="fas fa-arrow-left"></i> Cancel
            </a>
            <button type="submit" class="btn btn-primary">
                <i class="fas fa-save"></i> Save Changes
            </button>
        </div>
    </form>
</div>

<!-- Include Footer -->
<jsp:include page="../common/footer.jsp" />
</body>
</html>

