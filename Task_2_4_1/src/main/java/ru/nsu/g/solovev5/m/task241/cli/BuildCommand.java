package ru.nsu.g.solovev5.m.task241.cli;

import java.io.IOException;
import java.nio.file.Files;
import picocli.CommandLine;
import ru.nsu.g.solovev5.m.task241.core.models.ReportEntry;
import ru.nsu.g.solovev5.m.task241.core.models.Student;
import ru.nsu.g.solovev5.m.task241.core.models.StudyGroup;
import ru.nsu.g.solovev5.m.task241.core.models.Task;
import ru.nsu.g.solovev5.m.task241.core.services.BuildService;
import ru.nsu.g.solovev5.m.task241.core.services.ReportsRepository;

/**
 * A CLI command for building.
 */
@CommandLine.Command(
    name = "build",
    description = "checks if the task build success"
)
public class BuildCommand extends Command {
    private final BuildService buildService;
    private final ReportsRepository reportsRepository;

    /**
     * Creates a new build command.
     */
    public BuildCommand() {
        super();
        this.buildService = new BuildService();
        this.reportsRepository = new ReportsRepository(resolver);
    }

    @Override
    public Integer call() throws Exception {
        for (var group : config.studyGroups()) {
            checkGroup(group);
        }
        return 0;
    }

    /**
     * Tests a whole group for tasks building success.
     *
     * @param group a group of submitters
     */
    private void checkGroup(StudyGroup group) {
        System.out.println("Building group " + group.id());
        for (var student : group.students()) {
            checkStudent(student);
        }
    }

    /**
     * Tests a student for tasks building success.
     *
     * @param student a task submitter
     */
    private void checkStudent(Student student) {
        System.out.println("Building student " + student.name());
        for (var task : config.tasks()) {
            checkTask(student, task);
        }
    }

    /**
     * Tests a task for building success.
     *
     * @param student a task submitter
     * @param task    a checking task
     */
    private void checkTask(Student student, Task task) {
        System.out.println("Building task " + task.name());
        var taskPath = resolver.resolveTask(student, task);
        if (Files.notExists(taskPath)) {
            System.err.println("Task path not found. Nothing to do");
            return;
        }

        var path = resolver.resolveTask(student, task);
        try {

            var previousEntry = reportsRepository.load(student, task);
            var entryBuilder = new ReportEntry.Builder().from(previousEntry);
            if (buildService.successfulBuild(path)) {
                System.out.println("Build successful");
                reportsRepository.store(
                    entryBuilder.buildPassed(true).build()
                );
            } else {
                System.out.println("Build failed");
                reportsRepository.store(
                    entryBuilder.buildPassed(false).build()
                );
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
