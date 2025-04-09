package com.pricing.services.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {


    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss");


    public static LocalDateTime toLocalDateTime(String dateString) {
        return LocalDateTime.parse(dateString, FORMATTER);
    }
}
