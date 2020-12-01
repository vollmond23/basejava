package ru.javaops.webapp.model;

public enum ContactType {
    PHONE("Телефон"),
    EMAIL("Почта"),
    SKYPE("Skype"),
    HOMEPAGE("Домашняя страница"),
    GITHUB("Профиль GitHub"),
    LINKEDIN("Профиль LinkedIn"),
    STACKOVERFLOW("Профиль Stackoverflow");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
