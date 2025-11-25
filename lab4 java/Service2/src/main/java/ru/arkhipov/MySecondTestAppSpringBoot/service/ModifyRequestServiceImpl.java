package ru.arkhipov.MySecondTestAppSpringBoot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.arkhipov.MySecondTestAppSpringBoot.model.Request;

/**
 * Реализация сервиса модификации запроса.
 * В рамках ЛР4 изменяет поле source.
 */
@Slf4j
@Service
public class ModifyRequestServiceImpl implements ModifyRequestService {

    @Override
    public void modify(Request request) {
        request.setSource("Service1");
        log.info("Поле source в Request изменено на: {}", request.getSource());
    }
}