package com.shsoftvina.community.utils;

import java.time.*;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class DateUtils {

    public static boolean isDateInCurrentMonth(Instant date) {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime zonedDateTime = date.atZone(ZoneId.systemDefault());

        return zonedDateTime.getYear() == now.getYear() &&
                zonedDateTime.getMonth() == now.getMonth();
    }

    public static boolean isDateInCurrentWeek(Instant date) {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime zonedDateTime = date.atZone(ZoneId.systemDefault());

        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int currentWeek = now.get(weekFields.weekOfWeekBasedYear());
        int currentYear = now.get(ChronoField.YEAR);
        int createdWeek = zonedDateTime.get(weekFields.weekOfWeekBasedYear());
        int createdYear = zonedDateTime.get(ChronoField.YEAR);

        return createdYear == currentYear && createdWeek == currentWeek;
    }

    public static Instant mergeToInstant(LocalDate dateReq, LocalTime timeReq) {
        if(dateReq == null || timeReq == null) return null;
        LocalDateTime dateTimeReq = LocalDateTime.of(dateReq, timeReq);
        ZonedDateTime zonedDateTimeReq = dateTimeReq.atZone(ZoneId.systemDefault());
        return zonedDateTimeReq.toInstant();
    }

    public static LocalDate getLocalDate(Instant instant) {
        return instant.atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalTime getLocalTime(Instant instant) {
        return instant.atZone(ZoneId.systemDefault()).toLocalTime();
    }

    public static long getCountDayUtilToday(Instant instant) {
        LocalDate publishedDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate currentDate = LocalDate.now();
        return ChronoUnit.DAYS.between(publishedDate, currentDate);
    }
}
