package ru.arkhipov.MySecondTestAppSpringBoot.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.arkhipov.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.arkhipov.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.arkhipov.MySecondTestAppSpringBoot.model.Codes;
import ru.arkhipov.MySecondTestAppSpringBoot.model.ErrorCodes;
import ru.arkhipov.MySecondTestAppSpringBoot.model.ErrorMessages;
import ru.arkhipov.MySecondTestAppSpringBoot.model.Request;
import ru.arkhipov.MySecondTestAppSpringBoot.model.Response;
import ru.arkhipov.MySecondTestAppSpringBoot.service.ModifyRequestService;
import ru.arkhipov.MySecondTestAppSpringBoot.service.ModifyResponseService;
import ru.arkhipov.MySecondTestAppSpringBoot.service.ValidationService;
import ru.arkhipov.MySecondTestAppSpringBoot.util.DateTimeUtil;

import java.util.Date;

@Slf4j
@RestController
public class MyController {

    private final ValidationService validationService;
    private final ModifyResponseService modifyOperationUidResponseService;
    private final ModifyResponseService modifySystemTimeResponseService;
    private final ModifyRequestService modifyRequestService;
    private final RestTemplate restTemplate;

    @Autowired
    public MyController(ValidationService validationService,
                        @Qualifier("modifyOperationUidResponseService") ModifyResponseService modifyOperationUidResponseService,
                        @Qualifier("modifySystemTimeResponseService") ModifyResponseService modifySystemTimeResponseService,
                        ModifyRequestService modifyRequestService,
                        RestTemplate restTemplate) {
        this.validationService = validationService;
        this.modifyOperationUidResponseService = modifyOperationUidResponseService;
        this.modifySystemTimeResponseService = modifySystemTimeResponseService;
        this.modifyRequestService = modifyRequestService;
        this.restTemplate = restTemplate;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request,
                                             BindingResult bindingResult) {

        log.info("Сервис 1: получен запрос: {}", request);

        // время получения запроса сервисом 1 (для п.3, измерение времени)
        long service1ReceiveTime = System.currentTimeMillis();
        request.setSystemTime(String.valueOf(service1ReceiveTime));
        log.info("Сервис 1: время получения запроса (ms) = {}", service1ReceiveTime);

        Response response = Response.builder()
                .uid(request.getUid())
                .systemName("ERP")
                .build();

        log.info("Сервис 1: базовый ответ сформирован: {}", response);

        try {
            validationService.isValid(bindingResult);

            if ("123".equals(request.getUid())) {
                log.error("Сервис 1: получен неподдерживаемый uid=123, будет выброшено UnsupportedCodeException");
                throw new UnsupportedCodeException("Unsupported uid value: 123");
            }

            // успешная обработка
            response.setCode(Codes.success);
            response.setErrorCode(null);
            response.setErrorMessage(null);

            // модификация ответа отдельными сервисами
            modifyOperationUidResponseService.modify(response, request);
            modifySystemTimeResponseService.modify(response, request);

            // модификация запроса перед отправкой во второй сервис
            modifyRequestService.modify(request);
            log.info("Сервис 1: запрос после модификации перед отправкой в Сервис 2: {}", request);

            // отправка модифицированного запроса во второй сервис
            try {
                restTemplate.postForEntity("http://localhost:8084/feedback", request, Void.class);
                log.info("Сервис 1: модифицированный запрос успешно отправлен в Сервис 2");
            } catch (Exception ex) {
                log.error("Сервис 1: ошибка при отправке запроса в Сервис 2", ex);
            }

            log.info("Сервис 1: ответ после успешной модификации: {}", response);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (ValidationFailedException e) {
            response.setCode(Codes.failed);
            response.setErrorCode(ErrorCodes.ValidationException);
            response.setErrorMessage(ErrorMessages.ValidationError);
            log.error("Сервис 1: ошибка валидации запроса: {}", e.getMessage(), e);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (UnsupportedCodeException e) {
            response.setCode(Codes.failed);
            response.setErrorCode(ErrorCodes.UnsupportedCodeException);
            response.setErrorMessage(ErrorMessages.UnsupportedError);
            log.error("Сервис 1: ошибка неподдерживаемого кода: {}", e.getMessage(), e);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.setCode(Codes.failed);
            response.setErrorCode(ErrorCodes.UnknownException);
            response.setErrorMessage(ErrorMessages.UnknownError);
            log.error("Сервис 1: произошла непредвиденная ошибка", e);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}