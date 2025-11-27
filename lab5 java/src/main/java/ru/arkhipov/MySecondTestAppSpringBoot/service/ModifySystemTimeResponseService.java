package ru.arkhipov.MySecondTestAppSpringBoot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.arkhipov.MySecondTestAppSpringBoot.model.Request;
import ru.arkhipov.MySecondTestAppSpringBoot.model.Response;
import ru.arkhipov.MySecondTestAppSpringBoot.util.DateTimeUtil;

import java.util.Date;

/**
 * Сервис, изменяющий поле systemTime в ответе.
 */
@Slf4j
@Service("modifySystemTimeResponseService")
public class ModifySystemTimeResponseService implements ModifyResponseService {

    @Override
    public void modify(Response response, Request request) {
        String newTime = DateTimeUtil.getCustomFormat().format(new Date());
        response.setSystemTime(newTime);
        log.info("Поле systemTime изменено, новое значение: {}", response.getSystemTime());
    }
}