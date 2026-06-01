<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Person" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Dashboard - Shared Code Notebook</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous" />
</head>
<body>
<div class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <div>
            <h2>Dashboard</h2>
            <p>Welcome, <strong><%= ((Person) session.getAttribute("user")).getFirstName() %></strong></p>
        </div>
        <a href="<%= request.getContextPath() %>/logout" class="btn btn-outline-secondary">Logout</a>
    </div>

    <div class="card mb-3">
        <div class="card-body">
            <h5 class="card-title">My Projects</h5>
            <p class="card-text">No projects available yet. Create your first project to get started.</p>
            <a href="#" class="btn btn-primary disabled">Create Project</a>
        </div>
    </div>

    <div class="alert alert-info">Project CRUD and language associations will be added here next.</div>
</div>
</body>
</html>
