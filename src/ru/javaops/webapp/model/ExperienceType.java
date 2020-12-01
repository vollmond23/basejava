package ru.javaops.webapp.model;

public enum ExperienceType {
    JOB("Опыт работы"),
    EDUCATION("Образование");

    private final String title;

    ExperienceType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
