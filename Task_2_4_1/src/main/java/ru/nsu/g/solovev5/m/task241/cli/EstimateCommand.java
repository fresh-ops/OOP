package ru.nsu.g.solovev5.m.task241.cli;

import java.io.IOException;
import java.nio.file.Files;
import picocli.CommandLine;
import ru.nsu.g.solovev5.m.task241.core.models.ReportEntry;
import ru.nsu.g.solovev5.m.task241.core.models.Student;
import ru.nsu.g.solovev5.m.task241.core.models.StudyGroup;
import ru.nsu.g.solovev5.m.task241.core.models.Task;
import ru.nsu.g.solovev5.m.task241.core.services.ReportsRepository;
import ru.nsu.g.solovev5.m.task241.core.services.TaskEstimator;

/**
 * A CLI command to assign grades for tasks.
 */
@CommandLine.Command(
    name = "estimate",
    description = "assigns grades for the tasks"
)
public class EstimateCommand extends Command {
    private final ReportsRepository reportsRepository;

    /**
     * Creates a new EstimateCommand.
     */
    public EstimateCommand() {
        super();
        this.reportsRepository = new ReportsRepository(resolver);
    }

    @Override
    public Integer call() throws Exception {
        for (var group : config.studyGroups()) {
            estimateGroup(group);
        }
        return 0;
    }

    /**
     * Estimates group tasks.
     *
     * @param group the group of submitters
     */
    private void estimateGroup(StudyGroup group) {
        System.out.println("Estimating group " + group.id());
        for (var student : group.students()) {
            estimateStudent(student);
        }
    }

    /**
     * Estimates student tasks.
     *
     * @param student the task submitter
     */
    private void estimateStudent(Student student) {
        System.out.println("Estimating student " + student.name());
        for (var task : config.tasks()) {
            estimateTask(student, task);
        }
    }

    /**
     * Estimates the task.
     *
     * @param student the task submitter
     * @param task    the submitting task
     */
    private void estimateTask(Student student, Task task) {
        System.out.println("Estimating task " + task.name());
        var taskPath = resolver.resolveTask(student, task);
        if (Files.notExists(taskPath)) {
            System.err.println("Task path not found. Nothing to do");
            return;
        }

        var estimator = new TaskEstimator();
        try {
            var previousEntry = reportsRepository.load(student, task);
            var entryBuilder = new ReportEntry.Builder().from(previousEntry);
            var grade = estimator.estimate(previousEntry);
            System.out.println("Assigned grade " + grade);
            reportsRepository.store(
                entryBuilder.grade(grade).build()
            );
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
