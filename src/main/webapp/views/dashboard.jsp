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

    <div class="mb-3">
        <div class="d-flex justify-content-between align-items-center">
            <h5>My Projects</h5>
            <a href="<%= request.getContextPath() %>/projects/new" class="btn btn-primary">Create Project</a>
        </div>
        <div class="row mt-3">
            <% java.util.List<model.Project> projects = (java.util.List<model.Project>) request.getAttribute("projects");
               if (projects != null && !projects.isEmpty()) {
                   for (model.Project p : projects) {
            %>
            <div class="col-md-4 mb-3">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title"><%= p.getTitle() %></h5>
                        <p class="card-text"><%= p.getDescription() != null ? p.getDescription() : "" %></p>
                        <p class="text-muted">Created: <%= p.getCreatedAt() != null ? p.getCreatedAt().toString() : "-" %></p>
                        <p>Completed: <%= p.isCompleted() ? "Yes" : "No" %></p>
                    </div>
                </div>
            </div>
            <%       }
               } else { %>
            <div class="col-12">
                <div class="alert alert-info">No projects available yet. Create your first project to get started.</div>
            </div>
            <% } %>
        </div>
    </div>
</div>
</body>
</html>
