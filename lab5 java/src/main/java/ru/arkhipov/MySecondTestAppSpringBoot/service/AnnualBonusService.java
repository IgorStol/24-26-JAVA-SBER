package ru.arkhipov.MySecondTestAppSpringBoot.service;

import ru.arkhipov.MySecondTestAppSpringBoot.model.Positions;

/**
 * Сервис расчета премий.
 */
public interface AnnualBonusService {

    /**
     * Расчет годовой премии.
     *
     * @param salary годовая заработная плата
     * @param year   год, за который рассчитывается премия
     * @return размер годовой премии
     */
    int calculate(int salary, int year);

    /**
     * Расчет квартальной премии.
     *
     * @param salary   годовая заработная плата
     * @param year     год, за который рассчитывается премия
     * @param position должность сотрудника
     * @return размер квартальной премии
     */
    int calculateQuarterBonus(int salary, int year, Positions position);
}