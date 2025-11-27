package ru.arkhipov.MySecondTestAppSpringBoot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Модель ответного сообщения.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    /**
     * Уникальный идентификатор запроса.
     */
    private String uid;

    /**
     * Уникальный идентификатор операции.
     */
    private String operationUid;

    /**
     * Время формирования ответа.
     */
    private String systemTime;

    /**
     * Имя системы, сформировавшей ответ.
     */
    private String systemName;

    /**
     * Код результата обработки.
     */
    private Codes code;

    /**
     * Код ошибки в случае неуспешной обработки.
     */
    private ErrorCodes errorCode;

    /**
     * Сообщение об ошибке в случае неуспешной обработки.
     */
    private ErrorMessages errorMessage;

    /**
     * Рассчитанная годовая премия.
     */
    private Integer annualBonus;

    /**
     * Рассчитанная квартальная премия.
     */
    private Integer quarterBonus;
}