package ru.javaops.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class OrganizationSection implements Section {
    private final List<Experience> content = new ArrayList<>();

    public void addExperience(Experience experience) {
        content.add(experience);
    }

    public void clear() {
        content.clear();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Experience exp : content) {
            stringBuilder.append(exp).append('\n');
        }
        return stringBuilder.toString();
    }
}
