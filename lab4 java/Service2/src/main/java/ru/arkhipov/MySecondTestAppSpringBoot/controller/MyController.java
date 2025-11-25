package ru.arkhipov.MySecondTestAppSpringBoot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.arkhipov.MySecondTestAppSpringBoot.model.Codes;
import ru.arkhipov.MySecondTestAppSpringBoot.model.Request;
import ru.arkhipov.MySecondTestAppSpringBoot.model.Response;
import ru.arkhipov.MySecondTestAppSpringBoot.util.DateTimeUtil;

import java.util.Date;

/**
 * Контроллер второго сервиса.
 * Принимает модифицированный запрос от Сервиса 1 и выводит его в логи.
 */
@Slf4j
@RestController
public class MyController {

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@RequestBody Request request) {

        log.info("Сервис 2: получен модифицированный запрос от Сервиса 1: {}", request);

        // измерение времени между получением запроса в Сервисе 1 и Сервисе 2 (п.3)
        try {
            long service1Time = Long.parseLong(request.getSystemTime());
            long now = System.currentTimeMillis();
            long diff = now - service1Time;
            log.info("Сервис 2: разница времени между Сервисом 1 и Сервисом 2 = {} мс", diff);
        } catch (NumberFormatException e) {
            log.error("Сервис 2: не удалось разобрать systemTime из запроса: {}", request.getSystemTime(), e);
        }

        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .systemName("ERP")
                .code(Codes.success)
                .errorCode(null)
                .errorMessage(null)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}