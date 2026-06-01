package ru.nsu.g.solovev5.m.task241.cli;

import java.io.IOException;
import java.nio.file.Files;
import picocli.CommandLine;
import ru.nsu.g.solovev5.m.task241.core.models.ReportEntry;
import ru.nsu.g.solovev5.m.task241.core.models.Student;
import ru.nsu.g.solovev5.m.task241.core.models.StudyGroup;
import ru.nsu.g.solovev5.m.task241.core.models.Task;
import ru.nsu.g.solovev5.m.task241.core.services.ReportsRepository;
import ru.nsu.g.solovev5.m.task241.core.services.StyleCheckService;

/**
 * A CLI command for checking tasks' style.
 */
@CommandLine.Command(name = "style", description = "checks the styles")
public class StyleCommand extends Command {
    private final ReportsRepository reportsRepository;

    /**
     * Creates a new StyleCommand.
     */
    public StyleCommand() {
        super();
        this.reportsRepository = new ReportsRepository(resolver);
    }

    @Override
    public Integer call() {
        System.out.println("Checking styles");
        for (var group : config.studyGroups()) {
            checkGroup(group);
        }
        return 0;
    }

    /**
     * Checks a group for tasks' style.
     *
     * @param group a study group
     */
    private void checkGroup(StudyGroup group) {
        System.out.println("Checking group " + group.id());
        for (var student : group.students()) {
            checkStudent(student);
        }
    }

    /**
     * Checks a student for tasks' style.
     *
     * @param student a task submitter
     */
    private void checkStudent(Student student) {
        System.out.println("Checking " + student.name());
        var repository = resolver.resolveRepository(student);
        if (Files.notExists(repository)) {
            System.err.println(
                "Cannot find a repository path for "
                    + student.name()
                    + ". Try load repositories again"
            );
            return;
        }

        for (var task : config.tasks()) {
            checkTask(student, task);
        }
    }

    /**
     * Checks a task for style.
     *
     * @param student a task submitter
     * @param task    a checking task
     */
    private void checkTask(Student student, Task task) {
        System.out.println("Checking " + task.name());
        var taskPath = resolver.resolveTask(student, task);
        if (Files.notExists(taskPath)) {
            System.err.println("Task path not found. Nothing to do");
            return;
        }

        var checker = new StyleCheckService(resolver);
        try {
            var results = checker.check(student, task);
            var previousEntry = reportsRepository.load(student, task);
            var entryBuilder = new ReportEntry.Builder().from(previousEntry);

            if (results.totalEvents() > 0) {
                System.out.println("Check failed. Results[warnings/errors/total]: " + results);
                reportsRepository.store(
                    entryBuilder.stylePassed(false).build()
                );
            } else {
                System.out.println("Check success.");
                reportsRepository.store(
                    entryBuilder.stylePassed(true).build()
                );
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
