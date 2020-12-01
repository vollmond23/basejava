package ru.javaops.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class ListSection implements Section {
    private final List<String> content = new ArrayList<>();

    public void addString(String info) {
        content.add(info);
    }

    public void deleteString(int index) {
        content.remove(index);
    }

    public void editString(int index, String newString) {
        content.set(index, newString);
    }

    public void clear() {
        content.clear();
    }

    @Override
    public void printToConsole() {
        for (String info : content) {
            System.out.println("  - " + info);
        }
    }
}
