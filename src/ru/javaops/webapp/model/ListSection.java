package ru.javaops.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class ListSection implements Section {
    private final List<String> content = new ArrayList<>();

    public void addString(String info) {
        content.add(info);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String info : content) {
            stringBuilder.append("  - ").append(info).append('\n');
        }
        return stringBuilder.toString();
    }
}
