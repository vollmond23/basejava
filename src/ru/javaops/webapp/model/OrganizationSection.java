package ru.javaops.webapp.model;

import java.util.List;
import java.util.Objects;

public class OrganizationSection extends Section {
    private final List<Organization> content;

    public OrganizationSection(List<Organization> content) {
        Objects.requireNonNull(content, "Organizations must not be null");
        this.content = content;
    }

    public void addOrganization(Organization organization) {
        content.add(organization);
    }

    public List<Organization> getContent() {
        return content;
    }

    public void clear() {
        content.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationSection that = (OrganizationSection) o;

        return content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Organization exp : content) {
            stringBuilder.append(exp).append('\n');
        }
        return stringBuilder.toString();
    }
}
