package controller;

import dao.ProjectDAO;
import model.Project;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/projects")
public class ProjectEditServlet {
    private final ProjectDAO projectDAO;

    public ProjectEditServlet(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProject(@PathVariable Integer id) {
        Project project = projectDAO.getProjectById(id);
        if (project == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(project);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProject(@PathVariable Integer id, @RequestBody Map<String, Object> request) {
        String name = (String) request.get("name");
        String description = (String) request.get("description");

        if (name == null || name.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Project name is required"));
        }

        Project project = new Project();
        project.setId(id);
        project.setTitle(name);
        project.setDescription(description != null ? description : "");
        projectDAO.updateProject(project);

        return ResponseEntity.ok(Map.of("message", "Project updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Integer id) {
        projectDAO.deleteProject(id);
        return ResponseEntity.ok(Map.of("message", "Project deleted successfully"));
    }
}
