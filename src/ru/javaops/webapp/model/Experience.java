package ru.javaops.webapp.model;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Experience {
    private final String name;
    private final String weblink;
    private final List<Period> periods = new ArrayList<>();

    private static class Period {
        private final YearMonth dateBegin;
        private final YearMonth dateEnd;
        private final String description;
        private final String position;

        public Period(YearMonth dateBegin, YearMonth dateEnd, String description, String position) {
            Objects.requireNonNull(dateBegin);
            Objects.requireNonNull(description);
            this.dateBegin = dateBegin;
            this.dateEnd = dateEnd;
            this.description = description;
            this.position = position;
        }
    }

    public Experience(String name, String weblink) {
        Objects.requireNonNull(name);
        this.name = name;
        this.weblink = weblink;
    }

    public void addPeriod(YearMonth dateBegin, YearMonth dateEnd, String description, String position) {
        periods.add(new Period(dateBegin, dateEnd, description, position));
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        StringBuilder periodString = new StringBuilder();
        for (Period period : periods) {
            periodString.append("    ").append(period.dateBegin.format(formatter)).append(" - ").append((!Objects.isNull(period.dateEnd) ? period.dateEnd.format(formatter) : "Сейчас")).append(":  ").
                    append(!Objects.isNull(period.position) ? period.position + "\n    " : "").
                    append(period.description).append('\n');
        }
        return "    " + name + (!Objects.isNull(weblink) ? " (" + weblink + ")" : "") + '\n' + periodString.toString();
    }
}
