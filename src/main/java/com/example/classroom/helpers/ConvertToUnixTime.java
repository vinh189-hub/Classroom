package com.example.classroom.helpers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConvertToUnixTime {

    public static long convertUnixTime(String str){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        Instant instant = dateTime.toInstant(java.time.ZoneOffset.UTC);
        return instant.getEpochSecond();
    }
}
