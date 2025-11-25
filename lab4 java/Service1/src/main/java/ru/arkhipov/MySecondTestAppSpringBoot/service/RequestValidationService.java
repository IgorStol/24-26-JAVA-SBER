package ru.arkhipov.MySecondTestAppSpringBoot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.arkhipov.MySecondTestAppSpringBoot.exception.ValidationFailedException;

/**
 * Реализация сервиса валидации.
 */
@Slf4j
@Service
public class RequestValidationService implements ValidationService {

    @Override
    public void isValid(BindingResult bindingResult) throws ValidationFailedException {
        if (bindingResult.hasErrors()) {
            log.error("Ошибка валидации bindingResult: {}", bindingResult.getAllErrors());
            throw new ValidationFailedException(String.valueOf(bindingResult.getFieldError()));
        }
    }
}