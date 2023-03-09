package com.example.deliveryagent.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateParser {

    private DateParser(){}
    public static LocalDate parseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return LocalDate.parse(date, formatter);
    }

    public static LocalDateTime parseStartTime(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");
        return LocalDateTime.parse(date + " 00:00:00", formatter);
    }

    public static LocalDateTime parseEndTime(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss.SSS");
        return LocalDateTime.parse(date + " 23:59:59.999", formatter);
    }
}
