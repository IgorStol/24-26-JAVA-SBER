package ru.arkhipov.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.arkhipov.MySecondTestAppSpringBoot.exception.ValidationFailedException;

/**
 * Сервис валидации входящих данных.
 */
@Service
public interface ValidationService {

    void isValid(BindingResult bindingResult) throws ValidationFailedException;
}