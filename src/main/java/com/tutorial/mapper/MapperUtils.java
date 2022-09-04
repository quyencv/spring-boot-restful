package com.tutorial.mapper;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.TimeZone;

import org.mapstruct.Named;

public class MapperUtils {

    @Named(value = "convertStringToInstant")
    public static Instant convertStringToInstant(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDate localDate = LocalDate.parse(date, formatter);
            return localDate.atStartOfDay().atZone(TimeZone.getDefault().toZoneId()).toInstant();
        } catch (Exception e) {
            return Instant.now();
        }
    }

    @Named(value = "convertInstantToString")
    public static String convertInstantToString(Instant instant) {
        if (Objects.isNull(instant)) {
            return null;
        }
        return instant.atZone(TimeZone.getDefault().toZoneId()).toLocalDateTime()
                .format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
    }
}
