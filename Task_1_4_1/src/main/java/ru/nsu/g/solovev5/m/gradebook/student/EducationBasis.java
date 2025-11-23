package ru.nsu.g.solovev5.m.gradebook.student;

/**
 * The enumeration of all possible education bases.
 */
public enum EducationBasis {
    PAID("Платная"), BUDGETARY("Бюджетная");

    private final String name;

    EducationBasis(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
