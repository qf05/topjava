package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * GKislin
 * 07.01.2015.
 */
public class TimeUtil {
    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    private final static DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm");

    public static String dateFormatter(LocalDateTime dateTime) {
        return dateTime.format(format);
    }

    private final static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    public static LocalDateTime timeFormatter(String date) {
        return LocalDateTime.parse(date, dateTimeFormat);
    }
}
