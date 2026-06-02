package controller;

import dao.ProjectDAO;
import model.Project;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectListServlet {
    private final ProjectDAO projectDAO;

    public ProjectListServlet(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }

    @GetMapping
    public List<Project> getAllProjects() {
        return projectDAO.getAllProjects();
    }
}
