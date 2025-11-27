package ru.arkhipov.MySecondTestAppSpringBoot.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.arkhipov.MySecondTestAppSpringBoot.model.Positions;

/**
 * Unit-тесты для {@link AnnualBonusServiceImpl}.
 */
public class AnnualBonusServiceImplTest {

    private final AnnualBonusServiceImpl annualBonusService = new AnnualBonusServiceImpl();

    @Test
    void calculate_shouldReturnPositiveBonus_forNonLeapYear() {
        int salary = 365_000;
        int year = 2023; // не високосный год

        int bonus = annualBonusService.calculate(salary, year);

        // при нашей формуле ожидается 10 * (salary / daysInYear) = 10 * 1000 = 10000
        Assertions.assertEquals(10_000, bonus);
    }

    @Test
    void calculate_shouldUseLeapYearDays_forLeapYear() {
        int salary = 366_000;
        int year = 2024; // високосный год

        int bonus = annualBonusService.calculate(salary, year);

        // 366_000 / 366 = 1000; 1000 * 10 = 10_000
        Assertions.assertEquals(10_000, bonus);
    }

    @Test
    void calculateQuarterBonus_shouldWorkForManager() {
        int salary = 365_000;
        int year = 2023;
        Positions position = Positions.TL; // менеджер

        int quarterBonus = annualBonusService.calculateQuarterBonus(salary, year, position);

        // годовая премия 10_000, квартальная - четверть
        Assertions.assertEquals(2_500, quarterBonus);
    }

    @Test
    void calculateQuarterBonus_shouldThrowForNonManager() {
        int salary = 365_000;
        int year = 2023;
        Positions position = Positions.DEV; // не менеджер

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> annualBonusService.calculateQuarterBonus(salary, year, position));
    }
}