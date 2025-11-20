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

    private String uid;

    private String operationUid;

    private String systemTime;

    private String systemName;

    private String code;

    private String errorCode;

    private String errorMessage;
}