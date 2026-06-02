package model;

import java.time.LocalDateTime;
import java.util.List;

public class Project {
    private int id;
    private int ownerId;
    private String title;
    private String description;
    private boolean completed;
    private LocalDateTime createdAt;
    private String ownerName;
    private List<ProgrammingLanguage> languages;

    public Project() {
    }

    public Project(int id, int ownerId, String title, String description, boolean completed, LocalDateTime createdAt) {
        this.id = id;
        this.ownerId = ownerId;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<ProgrammingLanguage> getLanguages() {
        return languages;
    }

    public void setLanguages(List<ProgrammingLanguage> languages) {
        this.languages = languages;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
