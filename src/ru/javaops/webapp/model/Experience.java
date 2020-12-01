package ru.javaops.webapp.model;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Experience {
    private ExperienceType type;
    private String name;
    private String weblink;
    private List<Period> periods = new ArrayList<>();

    private class Period {
        private YearMonth dateBegin;
        private YearMonth dateEnd;
        private String position;
        private String description;

        public Period(YearMonth dateBegin, YearMonth dateEnd, String position, String description) {
            Objects.requireNonNull(dateBegin);
            Objects.requireNonNull(description);
            this.dateBegin = dateBegin;
            this.dateEnd = dateEnd;
            this.position = position;
            this.description = description;
        }
    }

    public Experience addEdu(String name, String weblink, String description, YearMonth dateBegin, YearMonth dateEnd) {
        Objects.requireNonNull(name);
        type = ExperienceType.EDUCATION;
        this.name = name;
        this.weblink = weblink;
        periods.add(new Period(dateBegin, dateEnd, null, description));
        return this;
    }

    public void addEduPeriod(YearMonth dateBegin, YearMonth dateEnd, String description) {
        periods.add(new Period(dateBegin, dateEnd, null, description));
    }

    public Experience addJob(String name, String weblink, String position, String description, YearMonth dateBegin, YearMonth dateEnd) {
        Objects.requireNonNull(name);
        type = ExperienceType.JOB;
        this.name = name;
        this.weblink = weblink;
        periods.add(new Period(dateBegin, dateEnd, position, description));
        return this;
    }

    public void addJobPeriod(YearMonth dateBegin, YearMonth dateEnd, String position, String description) {
        periods.add(new Period(dateBegin, dateEnd, position, description));
    }

    public ExperienceType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeblink() {
        return weblink;
    }

    public void setWeblink(String weblink) {
        this.weblink = weblink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Experience that = (Experience) o;

        if (type != that.type) return false;
        if (!name.equals(that.name)) return false;
        if (!Objects.equals(weblink, that.weblink)) return false;
        return periods.equals(that.periods);
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (weblink != null ? weblink.hashCode() : 0);
        result = 31 * result + periods.hashCode();
        return result;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        StringBuilder periodString = new StringBuilder();
        for (Period period : periods) {
            periodString.append("    ").append(period.dateBegin.format(formatter)).append(" - ").append((!Objects.isNull(period.dateEnd) ? period.dateEnd.format(formatter) : "Сейчас")).append(":  ").
                    append(!Objects.isNull(period.position) ? period.position +"\n    " : "").
                    append(period.description).append('\n');
        }
        return "    " + name + (!Objects.isNull(weblink) ? " (" + weblink + ")" : "") + '\n' + periodString.toString();
    }
}
