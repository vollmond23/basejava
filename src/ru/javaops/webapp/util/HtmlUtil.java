package ru.javaops.webapp.util;

import ru.javaops.webapp.model.Organization;

public class HtmlUtil {
    public static String formatDates(Organization.Position position) {
        return DateUtil.format(position.getDateBegin()) + " - " + DateUtil.format(position.getDateEnd());
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }
}
