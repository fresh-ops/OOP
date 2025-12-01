package ru.nsu.g.solovev5.m.gradebook.student;

/**
 * The enumeration of all possible education bases.
 */
public enum EducationBasis {
    /**
     * Fee-founded basis.
     */
    PAID("Платная"),
    /**
     * Budgetary-founded basis.
     */
    BUDGETARY("Бюджетная");

    private final String name;

    EducationBasis(String name) {
        this.name = name;
    }

    /**
     * Returns the name of this basis.
     *
     * @return the name of this basis
     */
    public String getName() {
        return name;
    }
}
