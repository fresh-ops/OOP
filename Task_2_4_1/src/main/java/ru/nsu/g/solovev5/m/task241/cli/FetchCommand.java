package ru.nsu.g.solovev5.m.task241.cli;

import java.io.IOException;
import picocli.CommandLine;
import ru.nsu.g.solovev5.m.task241.core.models.Student;
import ru.nsu.g.solovev5.m.task241.core.models.StudyGroup;
import ru.nsu.g.solovev5.m.task241.core.services.RepositoryService;

/**
 * A CLI command for fetching students' repository.
 */
@CommandLine.Command(
    name = "fetch",
    description = "fetches all repositories"
)
public class FetchCommand extends Command {
    @Override
    public Integer call() {
        try {
            for (var group : config.studyGroups()) {
                fetchGroup(group);
            }
        } catch (InterruptedException e) {
            System.err.println("Interrupted");
            return 1;
        }
        return 0;
    }

    /**
     * Fetches repositories of a study group.
     *
     * @param group a group of repository owners
     * @throws InterruptedException if the fetching process was interrupted
     */
    private void fetchGroup(StudyGroup group) throws InterruptedException {
        System.out.println("Fetching group " + group.id());
        for (var student : group.students()) {
            fetchStudent(student);
        }
    }

    /**
     * Fetches the student's repository.
     *
     * @param student a repository owner
     * @throws InterruptedException if the fetching process was interrupted
     */
    private void fetchStudent(Student student) throws InterruptedException {
        System.out.println("Fetching student " + student.name());
        var service = new RepositoryService(resolver);

        try {
            service.loadRepositoryIfNotLoaded(student);
            service.updateRepository(student);
        } catch (IOException | RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }
}
