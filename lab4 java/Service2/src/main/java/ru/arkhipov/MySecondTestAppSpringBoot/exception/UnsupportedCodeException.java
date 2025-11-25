package ru.arkhipov.MySecondTestAppSpringBoot.exception;

/**
 * Исключение, выбрасываемое при неподдерживаемом значении uid.
 */
public class UnsupportedCodeException extends Exception {

    public UnsupportedCodeException(String message) {
        super(message);
    }
}