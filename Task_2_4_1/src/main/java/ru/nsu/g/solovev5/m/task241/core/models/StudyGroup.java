package ru.nsu.g.solovev5.m.task241.core.models;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/**
 * A model of a university study group.
 *
 * @param id       the internal university group ID
 * @param students the students in this group
 */
public record StudyGroup(String id, Set<Student> students) implements Iterable<Student> {
    /**
     * Creates a new study group.
     *
     * @param id       the internal university group ID
     * @param students the students in this group
     * @throws NullPointerException     if either of the arguments is null
     * @throws IllegalArgumentException if the {@code students} is empty
     */
    public StudyGroup {
        Objects.requireNonNull(id, "ID must not be null");
        Objects.requireNonNull(students, "Students must not be null");
        if (students.isEmpty()) {
            throw new IllegalArgumentException("The group must have at least one student");
        }
    }

    /**
     * Returns the number of students in this group.
     *
     * @return the number of students in this group
     */
    public int size() {
        return students.size();
    }

    /**
     * Checks if the student studies in this group.
     *
     * @param student the student to check
     * @return {@code true} if the student is a part of this group, {@code false} otherwise
     */
    public boolean contains(Student student) {
        return students.contains(student);
    }

    @Override
    public Iterator<Student> iterator() {
        return students.iterator();
    }
}
