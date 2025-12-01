package ru.nsu.g.solovev5.m.gradebook.student;

/**
 * A Student class.
 *
 * @param name the name of this student
 * @param surname the surname of this student
 * @param educationBasis the basis of this student's education
 */
public record Student(String name, String surname, EducationBasis educationBasis) {
    /**
     * Creates a new Student with given name and surname. The education basis by default is
     * {@code EducationBasis.PAID}.
     *
     * @param name the name of this student
     * @param surname the surname of this student
     */
    public Student(String name, String surname) {
        this(name, surname, EducationBasis.PAID);
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }
}
