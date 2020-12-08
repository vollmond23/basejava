package ru.javaops.webapp.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization {
    private final Link homePage;
    private final List<Period> periods = new ArrayList<>();

    private static class Period {
        private final LocalDate dateBegin;
        private final LocalDate dateEnd;
        private final String description;
        private final String title;

        public Period(LocalDate dateBegin, LocalDate dateEnd, String title, String description) {
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
        addPeriod(dateBegin, dateEnd, title, description);
    }

    public void addPeriod(LocalDate dateBegin, LocalDate dateEnd, String title, String description) {
        periods.add(new Period(dateBegin, dateEnd, title, description));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!homePage.equals(that.homePage)) return false;
        return periods.equals(that.periods);
    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + periods.hashCode();
        return result;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        StringBuilder periodString = new StringBuilder();
        for (Period period : periods) {
            periodString.append('\n').append("    ").append(period.dateBegin.format(formatter)).append(" - ").append((!Objects.isNull(period.dateEnd) ? period.dateEnd.format(formatter) : "Сейчас")).
                    append(":  ").append(period.title).
                    append(!Objects.isNull(period.description) ? "\n    " + period.description : "");
        }
        return "  - " + homePage.getName() + (!Objects.isNull(homePage.getUrl()) ? " (" + homePage.getUrl() + ")" : "") + periodString.toString();
    }
}
