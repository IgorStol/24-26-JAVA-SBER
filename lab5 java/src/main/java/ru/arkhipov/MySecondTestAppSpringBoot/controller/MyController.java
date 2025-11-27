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
import ru.arkhipov.MySecondTestAppSpringBoot.service.AnnualBonusService;
import ru.arkhipov.MySecondTestAppSpringBoot.service.ModifyResponseService;
import ru.arkhipov.MySecondTestAppSpringBoot.service.ValidationService;

@Slf4j
@RestController
public class MyController {

    private final ValidationService validationService;
    private final ModifyResponseService modifyOperationUidResponseService;
    private final ModifyResponseService modifySystemTimeResponseService;
    private final AnnualBonusService annualBonusService;

    @Autowired
    public MyController(ValidationService validationService,
                        @Qualifier("modifyOperationUidResponseService") ModifyResponseService modifyOperationUidResponseService,
                        @Qualifier("modifySystemTimeResponseService") ModifyResponseService modifySystemTimeResponseService,
                        AnnualBonusService annualBonusService) {
        this.validationService = validationService;
        this.modifyOperationUidResponseService = modifyOperationUidResponseService;
        this.modifySystemTimeResponseService = modifySystemTimeResponseService;
        this.annualBonusService = annualBonusService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request,
                                             BindingResult bindingResult) {

        log.info("Получен запрос: {}", request);

        Response response = createBaseResponse(request);
        log.info("Базовый ответ сформирован: {}", response);

        try {
            validationService.isValid(bindingResult);

            if ("123".equals(request.getUid())) {
                log.error("Получен неподдерживаемый uid=123, будет выброшено UnsupportedCodeException");
                throw new UnsupportedCodeException("Unsupported uid value: 123");
            }

            fillSuccessResponse(response, request);
            log.info("Успешный ответ сформирован: {}", response);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (ValidationFailedException e) {
            fillErrorResponse(response, ErrorCodes.ValidationException, ErrorMessages.ValidationError);
            log.error("Ошибка валидации запроса: {}", e.getMessage(), e);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (UnsupportedCodeException e) {
            fillErrorResponse(response, ErrorCodes.UnsupportedCodeException, ErrorMessages.UnsupportedError);
            log.error("Ошибка неподдерживаемого кода: {}", e.getMessage(), e);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            fillErrorResponse(response, ErrorCodes.UnknownException, ErrorMessages.UnknownError);
            log.error("Произошла непредвиденная ошибка", e);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Response createBaseResponse(Request request) {
        return Response.builder()
                .uid(request.getUid())
                .systemName("ERP")
                .build();
    }

    private void fillSuccessResponse(Response response, Request request) {
        response.setCode(Codes.success);
        response.setErrorCode(null);
        response.setErrorMessage(null);

        modifyOperationUidResponseService.modify(response, request);
        modifySystemTimeResponseService.modify(response, request);

        if (request.getSalary() != null && request.getYear() != null) {
            int annualBonus = annualBonusService.calculate(request.getSalary(), request.getYear());
            response.setAnnualBonus(annualBonus);
            log.info("Рассчитана годовая премия: {}", annualBonus);

            if (request.getPosition() != null) {
                try {
                    int quarterBonus = annualBonusService.calculateQuarterBonus(
                            request.getSalary(),
                            request.getYear(),
                            request.getPosition()
                    );
                    response.setQuarterBonus(quarterBonus);
                    log.info("Рассчитана квартальная премия: {}", quarterBonus);
                } catch (IllegalArgumentException ex) {
                    log.warn("Квартальная премия не рассчитана: {}", ex.getMessage());
                }
            }
        }
    }

    private void fillErrorResponse(Response response, ErrorCodes errorCode, ErrorMessages errorMessage) {
        response.setCode(Codes.failed);
        response.setErrorCode(errorCode);
        response.setErrorMessage(errorMessage);
    }
}