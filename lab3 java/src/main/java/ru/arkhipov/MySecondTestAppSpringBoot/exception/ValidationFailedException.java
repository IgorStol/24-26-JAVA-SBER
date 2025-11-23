package ru.arkhipov.MySecondTestAppSpringBoot.exception;

/**
 * Исключение, сигнализирующее о неуспешной валидации входящих данных.
 */
public class ValidationFailedException extends Exception {

    public ValidationFailedException(String message) {
        super(message);
    }
}