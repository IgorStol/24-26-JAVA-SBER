package ru.arkhipov.MySecondTestAppSpringBoot.model;

/**
 * Перечисление должностей сотрудника.
 */
public enum Positions {

    /**
     * Разработчик (Developer).
     */
    DEV(false),

    /**
     * Специалист по работе с персоналом (HR-specialist).
     */
    HR(false),

    /**
     * Руководитель команды (Team Lead).
     */
    TL(true),

    /**
     * Владелец продукта (Product Owner).
     */
    PO(true),

    /**
     * Технический продакт-менеджер (Technical Product Manager).
     */
    TPM(true),

    /**
     * Технический директор (Chief Technology Officer).
     */
    CTO(true);

    /**
     * Признак того, что должность является управленческой.
     */
    private final boolean isManager;

    Positions(boolean isManager) {
        this.isManager = isManager;
    }

    public boolean isManager() {
        return isManager;
    }
}