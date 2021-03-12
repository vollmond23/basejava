package ru.javaops.webapp.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/yyyy");

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static LocalDate parse(String string) {
        if (HtmlUtil.isEmpty(string) || "Сейчас".equals(string)) {
            return NOW;
        }
        YearMonth yearMonth = YearMonth.parse(string, FORMATTER);
        return LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
    }

    public static String format(LocalDate localDate) {
        if (localDate == null) return "";
        return localDate.equals(NOW) ? "Сейчас" : localDate.format(FORMATTER);
    }
}
