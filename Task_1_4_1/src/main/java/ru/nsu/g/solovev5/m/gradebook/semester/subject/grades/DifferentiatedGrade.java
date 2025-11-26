package ru.nsu.g.solovev5.m.gradebook.semester.subject.grades;

/**
 * Represents a differentiated grade on a russian five-point scale.
 */
public enum DifferentiatedGrade implements Grade {
    FAIL("Неудовлетворительно", 2), SATISFACTORY("Удовлетворительно", 3),
    GOOD("Хорошо", 4), EXCELLENT("Отлично", 5);

    private final String name;
    private final int numeric;

    DifferentiatedGrade(String name, int numeric) {
        this.name = name;
        this.numeric = numeric;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isPositive() {
        return this != DifferentiatedGrade.FAIL;
    }

    @Override
    public boolean isDifferentiated() {
        return true;
    }

    @Override
    public int getNumeric() {
        return numeric;
    }
}
