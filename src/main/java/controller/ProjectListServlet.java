package controller;

import dao.ProjectDAO;
import model.Person;
import model.Project;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class ProjectListServlet extends HttpServlet {
    private final ProjectDAO projectDAO = new ProjectDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Person user = (Person) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        List<Project> projects = projectDAO.getProjectsByUser(user.getId());
        req.setAttribute("projects", projects);
        req.getRequestDispatcher("/views/dashboard.jsp").forward(req, resp);
    }
}
