package controller;

import dao.ProjectDAO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/projects")
public class ProjectDeleteServlet {
    private final ProjectDAO projectDAO;

    public ProjectDeleteServlet(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteProject(@PathVariable Integer id) {
        projectDAO.deleteProject(id);
        return ResponseEntity.ok(Map.of("message", "Project deleted successfully"));
    }
}
