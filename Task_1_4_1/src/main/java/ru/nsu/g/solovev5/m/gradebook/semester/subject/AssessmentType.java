package ru.nsu.g.solovev5.m.gradebook.semester.subject;

/**
 * Represents different assessments types.
 */
public enum AssessmentType {
    EXAM("Экзамен", true), CREDIT("Зачёт", false),
    DIFFERENTIATED_CREDIT("Дифференцированный зачёт", true), THESIS("Выпускная работа", true);

    public final String name;
    private final boolean isDifferentiated;

    AssessmentType(String name, boolean isDifferentiated) {
        this.name = name;
        this.isDifferentiated = isDifferentiated;
    }

    /**
     * Returns whether this assessment type has a differentiated grade or not.
     *
     * @return {@code true} if this assessment type has a differentiated grade,
     *     {@code false} otherwise
     */
    public boolean isDifferentiated() {
        return isDifferentiated;
    }
}
