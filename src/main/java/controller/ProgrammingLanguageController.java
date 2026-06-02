package controller;

import dao.ProgrammingLanguageDAO;
import model.ProgrammingLanguage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/languages")
public class ProgrammingLanguageController {
    private final ProgrammingLanguageDAO languageDAO;

    public ProgrammingLanguageController(ProgrammingLanguageDAO languageDAO) {
        this.languageDAO = languageDAO;
    }

    @GetMapping
    public List<ProgrammingLanguage> getAllLanguages() {
        return languageDAO.getAllLanguages();
    }
}
