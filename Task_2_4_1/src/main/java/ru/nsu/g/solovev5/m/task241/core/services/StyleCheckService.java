package ru.nsu.g.solovev5.m.task241.core.services;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;
import ru.nsu.g.solovev5.m.task241.core.models.Student;
import ru.nsu.g.solovev5.m.task241.core.models.StyleAuditResults;
import ru.nsu.g.solovev5.m.task241.core.models.Task;

/**
 * A service for checking task style.
 */
public class StyleCheckService {
    private static final String DEFAULT_STYLES_NAME = "default_styles.xml";
    private final PathResolver resolver;

    /**
     * Creates a new style check service.
     *
     * @param resolver a path resolver to access files in working directory
     */
    public StyleCheckService(final PathResolver resolver) {
        this.resolver = resolver;
    }

    /**
     * Checks the student task against style rules.
     *
     * @param student the submitting student
     * @param task    the task to check
     * @return a style audit results
     * @throws IOException if an I/O error occurred
     */
    public StyleAuditResults check(Student student, Task task) throws IOException {
        var checker = new Checker();
        try {
            checker.setModuleClassLoader(ClassLoader.getSystemClassLoader());
            checker.configure(
                ConfigurationLoader.loadConfiguration(
                    DEFAULT_STYLES_NAME,
                    new PropertiesExpander(new Properties())
                )
            );
            var collector = new StyleAuditStatisticCollector();
            checker.addListener(collector);
            checker.process(getCheckingFiles(student, task));
            return collector.freeze();
        } catch (CheckstyleException e) {
            System.err.println(e.getMessage());
        } finally {
            checker.destroy();
        }

        return new StyleAuditResults(0, 0);
    }

    private List<File> getCheckingFiles(Student student, Task task) throws IOException {
        var taskPath = resolver.resolveTask(student, task);
        try (var paths = Files.walk(taskPath)) {
            return paths.filter(Files::isRegularFile)
                .filter(this::isJavaFile)
                .map(Path::toFile)
                .toList();
        }
    }

    private boolean isJavaFile(Path path) {
        return path.getFileName().toString().endsWith(".java");
    }
}
