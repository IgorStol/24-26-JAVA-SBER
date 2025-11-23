package ru.arkhipov.MySecondTestAppSpringBoot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.arkhipov.MySecondTestAppSpringBoot.model.Request;
import ru.arkhipov.MySecondTestAppSpringBoot.model.Response;

/**
 * Сервис, изменяющий поле operationUid в ответе.
 */
@Slf4j
@Service("modifyOperationUidResponseService")
public class ModifyOperationUidResponseService implements ModifyResponseService {

    @Override
    public void modify(Response response, Request request) {
        response.setOperationUid(request.getOperationUid());
        log.info("Поле operationUid изменено, новое значение: {}", response.getOperationUid());
    }
}