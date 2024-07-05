package com.task.management.app.TaskManagement.utils;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

@Service
public class DateTimeUtils {


    private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern("dd-MM-yyyy HH:mm")
            .toFormatter();

    public static String convertEpocToDaysAndHours(long epochMilli) {
        return Instant.ofEpochMilli(epochMilli)
                .atZone(ZoneId.systemDefault())
                .format(DATE_TIME_FORMATTER);
    }

    public String convertEpocToDaysAndHours(Long epochMillis) {

        // Convert milliseconds to seconds
        long epochSeconds = epochMillis / 1000;

        // Calculate the total number of days
        long days = epochSeconds / (24 * 3600);

        // Calculate the remaining hours
        long hours = (epochSeconds % (24 * 3600)) / 3600;

        return ("Days: " + days + ", Hours: " + hours);
    }




}
