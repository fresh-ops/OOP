package ru.nsu.g.solovev5.m.task241.cli;

import java.io.IOException;
import java.nio.file.Files;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import picocli.CommandLine;
import ru.nsu.g.solovev5.m.task241.core.models.Student;
import ru.nsu.g.solovev5.m.task241.core.models.StudyGroup;
import ru.nsu.g.solovev5.m.task241.core.models.Task;
import ru.nsu.g.solovev5.m.task241.core.services.TestCoverageService;

/**
 * A CLI command for checking tasks test coverage.
 */
@CommandLine.Command(
    name = "coverage",
    description = "calculates test coverage"
)
public class CoverageCommand extends Command {
    @Override
    public Integer call() {
        try {
            for (var group : config.studyGroups()) {
                testGroup(group);
            }
        } catch (InterruptedException e) {
            System.err.println("Interrupted");
            return 1;
        }
        return 0;
    }

    /**
     * Tests a whole group for tasks test coverage.
     *
     * @param group a checking group
     * @throws InterruptedException if the checking process was interrupted
     */
    private void testGroup(StudyGroup group) throws InterruptedException {
        System.out.println("Testing group " + group.id());
        for (var student : group.students()) {
            testStudent(student);
        }
    }

    /**
     * Tests a student for tasks test coverage.
     *
     * @param student a checking student
     * @throws InterruptedException if the checking process was interrupted
     */
    private void testStudent(Student student) throws InterruptedException {
        System.out.println("Testing student " + student.name());
        for (var task : config.tasks()) {
            testTask(student, task);
        }
    }

    /**
     * Tests a task for tes coverage.
     *
     * @param student a task submitter
     * @param task a checking task
     * @throws InterruptedException if the checking process was interrupted
     */
    private void testTask(Student student, Task task) throws InterruptedException {
        System.out.println("Testing task " + task.name());
        var taskPath = resolver.resolveTask(student, task);
        if (Files.notExists(taskPath)) {
            System.err.println("Task path not found. Nothing to do");
            return;
        }

        var tester = new TestCoverageService();
        try {
            var coverage = tester.check(taskPath);
            System.out.println("Coverage is " + coverage);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            System.err.println(e.getMessage());
        }
    }
}
