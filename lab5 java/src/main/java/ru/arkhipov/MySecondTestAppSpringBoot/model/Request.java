package ru.arkhipov.MySecondTestAppSpringBoot.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Модель входящего запроса.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {

    /**
     * Уникальный идентификатор запроса.
     */
    @NotBlank
    @Size(max = 32)
    private String uid;

    /**
     * Уникальный идентификатор операции.
     */
    @NotBlank
    @Size(max = 32)
    private String operationUid;

    /**
     * Имя системы-инициатора.
     */
    private Systems systemName;

    /**
     * Время формирования запроса.
     */
    @NotBlank
    private String systemTime;

    /**
     * Источник инициирования запроса.
     */
    private String source;

    /**
     * Идентификатор коммуникации.
     */
    @Min(1)
    @Max(100000)
    private Integer communicationId;

    /**
     * Идентификатор шаблона сообщения.
     */
    private Integer templateId;

    /**
     * Код продукта.
     */
    private Integer productCode;

    /**
     * Код СМС-сообщения.
     */
    private Integer smsCode;

    /**
     * Годовая заработная плата сотрудника.
     */
    @Min(0)
    private Integer salary;

    /**
     * Год, за который рассчитывается премия.
     */
    @NotNull
    private Integer year;

    /**
     * Должность сотрудника.
     */
    private Positions position;

    @Override
    public String toString() {
        return "Request{" +
                "uid='" + uid + '\'' +
                ", operationUid='" + operationUid + '\'' +
                ", systemName=" + systemName +
                ", systemTime='" + systemTime + '\'' +
                ", source='" + source + '\'' +
                ", communicationId=" + communicationId +
                ", templateId=" + templateId +
                ", productCode=" + productCode +
                ", smsCode=" + smsCode +
                ", salary=" + salary +
                ", year=" + year +
                ", position=" + position +
                '}';
    }
}