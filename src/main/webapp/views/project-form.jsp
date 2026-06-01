<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>New Project - Shared Code Notebook</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous" />
</head>
<body>
<div class="container mt-5">
    <h2>Create Project</h2>
    <form method="post" action="<%= request.getContextPath() %>/projects/new">
        <div class="mb-3">
            <label class="form-label">Title</label>
            <input type="text" name="title" class="form-control" required />
        </div>
        <div class="mb-3">
            <label class="form-label">Description</label>
            <textarea name="description" class="form-control" rows="4" required></textarea>
        </div>
        <div class="mb-3">
            <label class="form-label">Languages</label>
            <div class="form-check">
                <input class="form-check-input" type="checkbox" name="languages" value="Java" id="langJava" />
                <label class="form-check-label" for="langJava">Java</label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="checkbox" name="languages" value="Python" id="langPython" />
                <label class="form-check-label" for="langPython">Python</label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="checkbox" name="languages" value="JavaScript" id="langJs" />
                <label class="form-check-label" for="langJs">JavaScript</label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="checkbox" name="languages" value="C#" id="langCsharp" />
                <label class="form-check-label" for="langCsharp">C#</label>
            </div>
        </div>
        <div class="form-check mb-3">
            <input class="form-check-input" type="checkbox" name="completed" id="completed" />
            <label class="form-check-label" for="completed">Completed</label>
        </div>
        <button class="btn btn-primary">Save</button>
    </form>
</div>
</body>
</html>
