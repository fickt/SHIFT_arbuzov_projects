package ru.cft.datemanager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateManager {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final int DATE_MINUS_YEARS = 16; // чтобы отображался 2007 год, ICQ как никак :)

    public static String getTodayDate() {
        LocalDateTime dateTime = LocalDateTime.now().minusYears(DATE_MINUS_YEARS);
        return " <" + dateTime.format(DATE_TIME_FORMATTER) + ">";
    }
}
