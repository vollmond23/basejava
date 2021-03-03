package ru.javaops.webapp.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter HTML_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static LocalDate parse(String string) {
        return LocalDate.parse(string, FORMATTER);
    }

    public static LocalDate parseHtml(String string) {
        return LocalDate.parse(string, HTML_FORMATTER);
    }

    public static String format(LocalDate localDate) {
        return localDate.format(FORMATTER);
    }
}
