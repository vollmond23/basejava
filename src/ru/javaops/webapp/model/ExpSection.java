package ru.javaops.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class ExpSection implements Section {
    private final List<Experience> content = new ArrayList<>();

    public void addExperience(Experience experience) {
        content.add(experience);
    }

    public void deleteExperience(int index) {
        content.remove(index);
    }

    public void editExperience(int index, Experience newExperience) {
        content.set(index, newExperience);
    }

    public void clear() {
        content.clear();
    }

    @Override
    public void printToConsole() {
        for (Experience exp : content) {
            System.out.println(exp);
        }
    }
}
