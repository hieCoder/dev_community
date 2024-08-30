package com.shsoftvina.community.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class CronUtil {

    public static String convertInstantToCron(Instant instant) {
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());

        int second = zonedDateTime.getSecond();
        int minute = zonedDateTime.getMinute();
        int hour = zonedDateTime.getHour();
        int dayOfMonth = zonedDateTime.getDayOfMonth();
        int month = zonedDateTime.getMonthValue();

        String cronExpression = String.format("%d %d %d %d %d ?",
                second, minute, hour, dayOfMonth, month);

        return cronExpression;
    }
}
