package ru.nsu.g.solovev5.m.task241.cli;

import java.io.IOException;
import java.nio.file.Files;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import picocli.CommandLine;
import ru.nsu.g.solovev5.m.task241.core.models.ReportEntry;
import ru.nsu.g.solovev5.m.task241.core.models.Student;
import ru.nsu.g.solovev5.m.task241.core.models.StudyGroup;
import ru.nsu.g.solovev5.m.task241.core.models.Task;
import ru.nsu.g.solovev5.m.task241.core.services.ReportsRepository;
import ru.nsu.g.solovev5.m.task241.core.services.TestCoverageService;

/**
 * A CLI command for checking tasks test coverage.
 */
@CommandLine.Command(
    name = "coverage",
    description = "calculates test coverage"
)
public class CoverageCommand extends Command {
    private static final double PASS_THRESHOLD = 80;
    private final ReportsRepository reportsRepository;

    /**
     * Creates a new CoverageCommand.
     */
    public CoverageCommand() {
        super();
        this.reportsRepository = new ReportsRepository(resolver);
    }

    @Override
    public Integer call() {
        for (var group : config.studyGroups()) {
            testGroup(group);
        }
        return 0;
    }

    /**
     * Tests a whole group for tasks test coverage.
     *
     * @param group a checking group
     */
    private void testGroup(StudyGroup group) {
        System.out.println("Testing group " + group.id());
        for (var student : group.students()) {
            testStudent(student);
        }
    }

    /**
     * Tests a student for tasks test coverage.
     *
     * @param student a checking student
     */
    private void testStudent(Student student) {
        System.out.println("Testing student " + student.name());
        for (var task : config.tasks()) {
            testTask(student, task);
        }
    }

    /**
     * Tests a task for test coverage.
     *
     * @param student a task submitter
     * @param task    a checking task
     */
    private void testTask(Student student, Task task) {
        System.out.println("Testing task " + task.name());
        var taskPath = resolver.resolveTask(student, task);
        if (Files.notExists(taskPath)) {
            System.err.println("Task path not found. Nothing to do");
            return;
        }

        var tester = new TestCoverageService();
        try {
            var previousEntry = reportsRepository.load(student, task);
            var entryBuilder = new ReportEntry.Builder().from(previousEntry);
            var coverage = tester.check(taskPath);
            System.out.println("Coverage is " + coverage);
            reportsRepository.store(
                entryBuilder.coveragePassed(coverage > PASS_THRESHOLD).build()
            );
        } catch (IOException | ParserConfigurationException | SAXException e) {
            System.err.println(e.getMessage());
        }
    }
}
