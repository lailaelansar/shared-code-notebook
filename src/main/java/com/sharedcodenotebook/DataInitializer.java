package com.sharedcodenotebook;

import dao.ProgrammingLanguageDAO;
import model.ProgrammingLanguage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(DataInitializer.class);

    private final ProgrammingLanguageDAO languageDAO;

    public DataInitializer(ProgrammingLanguageDAO languageDAO) {
        this.languageDAO = languageDAO;
    }

    @Override
    public void run(String... args) throws Exception {
        List<ProgrammingLanguage> existing = languageDAO.getAllLanguages();
        if (existing == null || existing.isEmpty()) {
            String[] defaults = {"Java", "Python", "JavaScript", "C#", "Go", "Ruby", "Kotlin", "TypeScript"};
            for (String name : defaults) {
                int id = languageDAO.findOrCreateByName(name);
                LOG.info("Seeded language {} -> id={}", name, id);
            }
            LOG.info("Inserted default programming languages as table was empty.");
        } else {
            LOG.info("Programming languages present: {} entries", existing.size());
        }
    }
}
