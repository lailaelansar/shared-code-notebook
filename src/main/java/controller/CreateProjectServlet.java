package controller;

import dao.ProjectDAO;
import dao.ProgrammingLanguageDAO;
import model.Person;
import model.Project;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CreateProjectServlet extends HttpServlet {
    private final ProjectDAO projectDAO = new ProjectDAO();
    private final ProgrammingLanguageDAO languageDAO = new ProgrammingLanguageDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/project-form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Person user = (Person) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String[] languages = req.getParameterValues("languages");
        boolean completed = req.getParameter("completed") != null;

        Project project = new Project();
        project.setOwnerId(user.getId());
        project.setTitle(title);
        project.setDescription(description);
        project.setCompleted(completed);

        int projectId = projectDAO.createProjectReturnId(project);
        if (projectId > 0 && languages != null) {
            List<String> langs = Arrays.asList(languages);
            for (String lname : langs) {
                int langId = languageDAO.findOrCreateByName(lname.trim());
                if (langId > 0) {
                    languageDAO.linkProjectLanguage(projectId, langId);
                }
            }
        }

        resp.sendRedirect(req.getContextPath() + "/projects");
    }
}
