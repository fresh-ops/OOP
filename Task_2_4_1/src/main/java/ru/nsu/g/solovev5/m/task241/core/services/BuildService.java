package ru.nsu.g.solovev5.m.task241.core.services;

import java.nio.file.Path;
import org.gradle.api.GradleException;
import org.gradle.tooling.GradleConnector;

/**
 * A service for building projects.
 */
public class BuildService {
    /**
     * Checks if the passed projects builds successfully.
     *
     * @param project a path to the project
     * @return {@code true} if the projects builds successfully, {@code false} otherwise
     */
    public boolean successfulBuild(Path project) {
        var connector = GradleConnector.newConnector();
        connector.forProjectDirectory(project.toFile());

        try (var connection = connector.connect()) {
            var launcher = connection.newBuild();

            launcher.forTasks("test", "jacocoTestReport");
            launcher.run();
            return true;
        } catch (GradleException e) {
            return false;
        }
    }
}
