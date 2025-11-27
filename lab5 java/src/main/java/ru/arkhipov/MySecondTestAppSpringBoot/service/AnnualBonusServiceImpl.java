package ru.arkhipov.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.arkhipov.MySecondTestAppSpringBoot.model.Positions;

/**
 * Реализация сервиса расчета премий.
 */
@Service
public class AnnualBonusServiceImpl implements AnnualBonusService {

    @Override
    public int calculate(int salary, int year) {
        int daysInYear = isLeapYear(year) ? 366 : 365;
        if (salary <= 0 || daysInYear <= 0) {
            return 0;
        }
        // Простая формула: дневная ставка * 10 условных дней премии.
        return (salary / daysInYear) * 10;
    }

    @Override
    public int calculateQuarterBonus(int salary, int year, Positions position) {
        if (position == null || !position.isManager()) {
            throw new IllegalArgumentException("Квартальная премия рассчитывается только для управленцев.");
        }
        int annualBonus = calculate(salary, year);
        // Квартальная премия - четверть годовой.
        return annualBonus / 4;
    }

    /**
     * Проверка года на високосность.
     *
     * @param year год
     * @return true, если год високосный, иначе false
     */
    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }
}