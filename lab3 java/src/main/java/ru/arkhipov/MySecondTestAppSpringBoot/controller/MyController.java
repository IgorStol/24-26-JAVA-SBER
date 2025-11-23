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
import ru.arkhipov.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.arkhipov.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.arkhipov.MySecondTestAppSpringBoot.model.Codes;
import ru.arkhipov.MySecondTestAppSpringBoot.model.ErrorCodes;
import ru.arkhipov.MySecondTestAppSpringBoot.model.ErrorMessages;
import ru.arkhipov.MySecondTestAppSpringBoot.model.Request;
import ru.arkhipov.MySecondTestAppSpringBoot.model.Response;
import ru.arkhipov.MySecondTestAppSpringBoot.service.ModifyResponseService;
import ru.arkhipov.MySecondTestAppSpringBoot.service.ValidationService;

@Slf4j
@RestController
public class MyController {

    private final ValidationService validationService;
    private final ModifyResponseService modifyOperationUidResponseService;
    private final ModifyResponseService modifySystemTimeResponseService;

    @Autowired
    public MyController(ValidationService validationService,
                        @Qualifier("modifyOperationUidResponseService") ModifyResponseService modifyOperationUidResponseService,
                        @Qualifier("modifySystemTimeResponseService") ModifyResponseService modifySystemTimeResponseService) {
        this.validationService = validationService;
        this.modifyOperationUidResponseService = modifyOperationUidResponseService;
        this.modifySystemTimeResponseService = modifySystemTimeResponseService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request,
                                             BindingResult bindingResult) {

        log.info("Получен запрос: {}", request);

        Response response = Response.builder()
                .uid(request.getUid())
                .systemName("ERP")
                .build();

        log.info("Базовый ответ сформирован: {}", response);

        try {
            validationService.isValid(bindingResult);

            if ("123".equals(request.getUid())) {
                log.error("Получен неподдерживаемый uid=123, будет выброшено UnsupportedCodeException");
                throw new UnsupportedCodeException("Unsupported uid value: 123");
            }

            // успешная обработка
            response.setCode(Codes.success);
            response.setErrorCode(null);
            response.setErrorMessage(null);

            // модификация ответа отдельными сервисами
            modifyOperationUidResponseService.modify(response, request);
            modifySystemTimeResponseService.modify(response, request);

            log.info("Ответ после успешной модификации: {}", response);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (ValidationFailedException e) {
            response.setCode(Codes.failed);
            response.setErrorCode(ErrorCodes.ValidationException);
            response.setErrorMessage(ErrorMessages.ValidationError);
            log.error("Ошибка валидации запроса: {}", e.getMessage(), e);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (UnsupportedCodeException e) {
            response.setCode(Codes.failed);
            response.setErrorCode(ErrorCodes.UnsupportedCodeException);
            response.setErrorMessage(ErrorMessages.UnsupportedError);
            log.error("Ошибка неподдерживаемого кода: {}", e.getMessage(), e);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.setCode(Codes.failed);
            response.setErrorCode(ErrorCodes.UnknownException);
            response.setErrorMessage(ErrorMessages.UnknownError);
            log.error("Произошла непредвиденная ошибка", e);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}