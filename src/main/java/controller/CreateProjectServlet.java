package controller;

import dao.ProjectDAO;
import dao.ProgrammingLanguageDAO;
import model.Project;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/projects")
public class CreateProjectServlet {
    private final ProjectDAO projectDAO;
    private final ProgrammingLanguageDAO languageDAO;

    public CreateProjectServlet(ProjectDAO projectDAO, ProgrammingLanguageDAO languageDAO) {
        this.projectDAO = projectDAO;
        this.languageDAO = languageDAO;
    }

    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody Map<String, Object> request) {
        String name = (String) request.get("name");
        String description = (String) request.get("description");
        List<Integer> languages = (List<Integer>) request.get("languages");

        if (name == null || name.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Project name is required"));
        }

        Project project = new Project();
        project.setTitle(name);
        project.setDescription(description != null ? description : "");

        int projectId = projectDAO.createProjectReturnId(project);
        if (projectId > 0 && languages != null) {
            for (Integer langId : languages) {
                languageDAO.linkProjectLanguage(projectId, langId);
            }
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", projectId, "message", "Project created successfully"));
    }
}
