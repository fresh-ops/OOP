package ru.nsu.g.solovev5.m.gradebook.semester.subject;

/**
 * Represents different assessments types.
 */
public enum AssessmentType {
    /**
     * Examined subject.
     */
    EXAM("Экзамен", true),
    /**
     * Credit subject.
     */
    CREDIT("Зачёт", false),
    /**
     * Differentiated subject.
     */
    DIFFERENTIATED_CREDIT("Дифференцированный зачёт", true),
    /**
     * Final thesis work.
     */
    THESIS("Выпускная работа", true);

    private final String name;
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

    /**
     * Returns the name of this assessment type.
     *
     * @return the name of this assessment type
     */
    public String getName() {
        return name;
    }
}
