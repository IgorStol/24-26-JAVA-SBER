package ru.arkhipov.MySecondTestAppSpringBoot.service;

import ru.arkhipov.MySecondTestAppSpringBoot.model.Request;
import ru.arkhipov.MySecondTestAppSpringBoot.model.Response;

/**
 * Сервис изменения исходящего ответа.
 */
public interface ModifyResponseService {

    void modify(Response response, Request request);
}