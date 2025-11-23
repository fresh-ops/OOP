package ru.nsu.g.solovev5.m.gradebook.semester.subject.grades;

/**
 * Represents a binary grade in terms of pass or fail.
 */
public enum CreditGrade implements Grade {
    FAIL("Незачёт"), PASS("Зачёт");

    public final String name;

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
}
