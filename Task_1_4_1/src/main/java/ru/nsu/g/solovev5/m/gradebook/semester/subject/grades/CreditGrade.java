package ru.nsu.g.solovev5.m.gradebook.semester.subject.grades;

/**
 * Represents a binary grade in terms of pass or fail.
 */
public enum CreditGrade implements Grade {
    /**
     * Failed subject.
     */
    FAIL("Незачёт"),
    /**
     * Passed subject.
     */
    PASS("Зачёт");

    private final String name;

    CreditGrade(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isPositive() {
        return this != FAIL;
    }

    @Override
    public boolean isDifferentiated() {
        return false;
    }
}
