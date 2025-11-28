package ru.nsu.g.solovev5.m.gradebook;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.OptionalDouble;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.g.solovev5.m.gradebook.exception.EmptyGradeBookException;
import ru.nsu.g.solovev5.m.gradebook.exception.WrongSemestersOrderException;
import ru.nsu.g.solovev5.m.gradebook.semester.Semester;
import ru.nsu.g.solovev5.m.gradebook.semester.subject.AssessmentType;
import ru.nsu.g.solovev5.m.gradebook.semester.subject.Subject;
import ru.nsu.g.solovev5.m.gradebook.semester.subject.grades.CreditGrade;
import ru.nsu.g.solovev5.m.gradebook.semester.subject.grades.DifferentiatedGrade;
import ru.nsu.g.solovev5.m.gradebook.student.EducationBasis;
import ru.nsu.g.solovev5.m.gradebook.student.Student;

class GradeBookTest {
    GradeBook book;

    static final Semester firstSemester = Semester.of(1,
        Subject.of("Математический анализ", AssessmentType.EXAM, DifferentiatedGrade.EXCELLENT),
        Subject.of("Линейная алгебра", AssessmentType.EXAM, DifferentiatedGrade.GOOD),
        Subject.of("Программирование", AssessmentType.CREDIT, CreditGrade.PASS),
        Subject.of("Дискретная математика", AssessmentType.CREDIT, CreditGrade.PASS)
    );

    static final Semester secondSemester = Semester.of(2,
        Subject.of("Математический анализ", AssessmentType.EXAM, DifferentiatedGrade.SATISFACTORY),
        Subject.of("Физика", AssessmentType.EXAM, DifferentiatedGrade.EXCELLENT),
        Subject.of("Алгоритмы и структуры данных", AssessmentType.EXAM, DifferentiatedGrade.GOOD),
        Subject.of("Теория вероятностей", AssessmentType.CREDIT, CreditGrade.PASS),
        Subject.of("Английский язык", AssessmentType.CREDIT, CreditGrade.PASS)
    );

    static final Semester excellentSemester = Semester.of(2,
        Subject.of("Математический анализ", AssessmentType.EXAM, DifferentiatedGrade.EXCELLENT),
        Subject.of("Физика", AssessmentType.EXAM, DifferentiatedGrade.EXCELLENT)
    );

    static final Semester thirdSemester = Semester.of(3,
        Subject.of("Физика", AssessmentType.EXAM, DifferentiatedGrade.EXCELLENT),
        Subject.of("Базы данных", AssessmentType.EXAM, DifferentiatedGrade.EXCELLENT),
        Subject.of("Операционные системы", AssessmentType.EXAM, DifferentiatedGrade.EXCELLENT),
        Subject.of("Компьютерные сети", AssessmentType.CREDIT, CreditGrade.PASS),
        Subject.of("Теория автоматов", AssessmentType.CREDIT, CreditGrade.PASS),
        Subject.of("Квалификационная работа", AssessmentType.THESIS, DifferentiatedGrade.EXCELLENT)
    );

    @BeforeEach
    void setUp() {
        book = new GradeBook(new Student("Митрофан", "Простаков"), 35);
    }

    @Test
    void checkAddingValidSemester() {
        assertAll("Adding semesters in chronological order should not cause any exception",
            () -> assertDoesNotThrow(() -> book.add(firstSemester)),
            () -> assertDoesNotThrow(() -> book.add(secondSemester)),
            () -> assertDoesNotThrow(() -> book.add(thirdSemester))
        );
    }

    @Test
    void checkAddingInvalidSemester() {
        assertThrows(
            WrongSemestersOrderException.class,
            () -> book.add(secondSemester),
            "Misordered semester should cause an exception"
        );
    }

    @Test
    void checkAverage() {
        assertEquals(
            OptionalDouble.empty(), book.average(),
            "If a grade book doesn't have any differentiated, the result should be empty"
        );

        book.add(firstSemester);
        book.add(secondSemester);

        assertEquals(
            OptionalDouble.of(4.2), book.average(),
            "The result of average method is average around all the differentiated grades "
                + "for all semesters"
        );
    }

    @Test
    void checkBudget() {
        var budgetary = new GradeBook(
            new Student("Александр", "Чацкий", EducationBasis.BUDGETARY),
            35
        );
        assertTrue(
            budgetary.canBeBudget(),
            "The student already with budgetary-basis stays with it"
        );

        assertThrows(
            EmptyGradeBookException.class,
            () -> book.canBeBudget(),
            "If the grade book is empty, the exception should be thrown"
        );

        book.add(firstSemester);
        assertTrue(
            book.canBeBudget(),
            "If there is no SATISFACTORY grades, the method should return true"
        );

        book.add(secondSemester);
        assertFalse(
            book.canBeBudget(),
            "If there is SATISFACTORY grades, the method should return false"
        );
    }

    @Test
    void checkScholarship() {
        assertThrows(
            EmptyGradeBookException.class,
            () -> book.canHaveIncreasedScholarship(),
            "If the grade book is empty, the exception should be thrown"
        );

        book.add(firstSemester);
        assertTrue(
            book.canHaveIncreasedScholarship(),
            "If there are only EXCELLENT and GOOD, the increased scholarship is available"
        );

        book.add(secondSemester);
        assertFalse(
            book.canHaveIncreasedScholarship(),
            "The increased scholarship is available only there are no SATISFACTORY or FAIl"
        );
    }

    @Test
    void checkHonor() {
        assertTrue(
            book.canBeHonored(),
            "A new student always have a chance to get a diploma with honor"
        );

        book.add(firstSemester);
        assertFalse(
            book.canBeHonored(),
            "There must be at least 75% of EXCELLENT grades for diploma with honor"
        );

        book.add(secondSemester);
        assertFalse(
            book.canBeHonored(),
            "If there are a SATISFACTORY grade, the diploma cannot be with honor"
        );

        var honor = new GradeBook(new Student("Александр", "Чацкий", EducationBasis.BUDGETARY), 35);
        honor.add(firstSemester);
        honor.add(excellentSemester);
        honor.add(thirdSemester);

        assertTrue(
            honor.canBeHonored(),
            "A grade book with at least 75% of EXCELLENT, no SATISFACTORY and EXCELLENT for thesis "
                + "is a diploma with honor"
        );
    }
}