package ru.javaops.webapp.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Link homePage;
    private final List<Position> positions = new ArrayList<>();

    private static class Position implements Serializable {
        private static final long serialVersionUID = 1L;

        private final LocalDate dateBegin;
        private final LocalDate dateEnd;
        private final String description;
        private final String title;

        public Position(LocalDate dateBegin, LocalDate dateEnd, String title, String description) {
            Objects.requireNonNull(dateBegin, "dateBegin must not be null");
            Objects.requireNonNull(title, "title must not be null");
            this.dateBegin = dateBegin;
            this.dateEnd = dateEnd;
            this.title = title;
            this.description = description;
        }
    }

    public Organization(String name, String url, LocalDate dateBegin, LocalDate dateEnd, String title, String description) {
        homePage = new Link(name, url);
        addPosition(dateBegin, dateEnd, title, description);
    }

    public void addPosition(LocalDate dateBegin, LocalDate dateEnd, String title, String description) {
        positions.add(new Position(dateBegin, dateEnd, title, description));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!homePage.equals(that.homePage)) return false;
        return positions.equals(that.positions);
    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + positions.hashCode();
        return result;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        StringBuilder periodString = new StringBuilder();
        for (Position position : positions) {
            periodString.append('\n').append("    ").append(position.dateBegin.format(formatter)).append(" - ").append((!Objects.isNull(position.dateEnd) ? position.dateEnd.format(formatter) : "Сейчас")).
                    append(":  ").append(position.title).
                    append(!Objects.isNull(position.description) ? "\n    " + position.description : "");
        }
        return "  - " + homePage.getName() + (!Objects.isNull(homePage.getUrl()) ? " (" + homePage.getUrl() + ")" : "") + periodString.toString();
    }
}
