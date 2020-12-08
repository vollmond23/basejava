package ru.javaops.webapp.model;

import java.util.List;
import java.util.Objects;

public class ListSection extends Section {
    private final List<String> content;

    public ListSection(List<String> content) {
        Objects.requireNonNull(content, "listsection must not be null");
        this.content = content;
    }

    public void addString(String info) {
        content.add(info);
    }

    public List<String> getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;

        return content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return content.hashCode();
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