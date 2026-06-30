package com.example.warehouse.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private DateUtil() {
    }

    public static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String format(LocalDateTime dateTime) {

        if (dateTime == null) {
            return null;
        }

        return dateTime.format(FORMATTER);
    }

    public static LocalDateTime parse(String date) {

        return LocalDateTime.parse(date, FORMATTER);
    }

    public static LocalDateTime now() {

        return LocalDateTime.now();
    }
}