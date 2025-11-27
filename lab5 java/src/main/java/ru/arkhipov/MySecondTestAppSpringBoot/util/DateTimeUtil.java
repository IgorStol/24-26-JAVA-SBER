package ru.arkhipov.MySecondTestAppSpringBoot.util;

import java.text.SimpleDateFormat;

/**
 * Утилитный класс для работы с датой и временем.
 */
public final class DateTimeUtil {

    private DateTimeUtil() {
    }

    public static SimpleDateFormat getCustomFormat() {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    }
}