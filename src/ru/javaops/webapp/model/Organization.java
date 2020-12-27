package ru.javaops.webapp.model;

import ru.javaops.webapp.util.DateUtil;
import ru.javaops.webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;

    private Link homePage;
    private List<Position> positions = new ArrayList<>();

    @XmlAccessorType(XmlAccessType.FIELD)

    public static class Position implements Serializable {
        private static final long serialVersionUID = 1L;
        @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
        private LocalDate dateBegin;
        @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
        private LocalDate dateEnd;
        private String title;
        private String description;

        public Position() {
        }

        public Position(LocalDate dateBegin, LocalDate dateEnd, String title, String description) {
            Objects.requireNonNull(dateBegin, "dateBegin must not be null");
            Objects.requireNonNull(dateEnd, "dateEnd must not be null");
            Objects.requireNonNull(title, "title must not be null");
            this.dateBegin = dateBegin;
            this.dateEnd = dateEnd;
            this.title = title;
            this.description = description;
        }

        public LocalDate getDateBegin() {
            return dateBegin;
        }

        public LocalDate getDateEnd() {
            return dateEnd;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Position position = (Position) o;

            if (!dateBegin.equals(position.dateBegin)) return false;
            if (!dateEnd.equals(position.dateEnd)) return false;
            if (!Objects.equals(description, position.description))
                return false;
            return title.equals(position.title);
        }

        @Override
        public int hashCode() {
            int result = dateBegin.hashCode();
            result = 31 * result + dateEnd.hashCode();
            result = 31 * result + (description != null ? description.hashCode() : 0);
            result = 31 * result + title.hashCode();
            return result;
        }
    }

    public Organization() {
    }

    public Organization(String name, String url, LocalDate dateBegin, LocalDate dateEnd, String title, String description) {
        homePage = new Link(name, url);
        addPosition(dateBegin, dateEnd, title, description);
    }

    public Organization(Link homePage, List<Position> positionList) {
        this.homePage = homePage;
        this.positions = positionList;
    }

    public void addPosition(LocalDate dateBegin, LocalDate dateEnd, String title, String description) {
        positions.add(new Position(dateBegin, dateEnd, title, description));
    }

    public Link getHomePage() {
        return homePage;
    }

    public List<Position> getPositions() {
        return positions;
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
            periodString.append('\n').append("    ").append(position.dateBegin.format(formatter)).append(" - ").append((!position.dateEnd.equals(DateUtil.NOW) ? position.dateEnd.format(formatter) : "Сейчас")).
                    append(":  ").append(position.title).
                    append(!Objects.isNull(position.description) ? "\n    " + position.description : "");
        }
        return "  - " + homePage.getName() + (!Objects.isNull(homePage.getUrl()) ? " (" + homePage.getUrl() + ")" : "") + periodString.toString();
    }
}
