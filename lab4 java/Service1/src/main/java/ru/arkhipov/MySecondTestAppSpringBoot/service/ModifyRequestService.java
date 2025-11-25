package ru.arkhipov.MySecondTestAppSpringBoot.service;

import ru.arkhipov.MySecondTestAppSpringBoot.model.Request;

/**
 * Сервис модификации входящего запроса перед отправкой во второй сервис.
 */
public interface ModifyRequestService {

    void modify(Request request);
}