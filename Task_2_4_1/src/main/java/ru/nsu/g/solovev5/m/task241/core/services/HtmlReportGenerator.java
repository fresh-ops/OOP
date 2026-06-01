package ru.nsu.g.solovev5.m.task241.core.services;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import ru.nsu.g.solovev5.m.task241.core.models.ReportEntry;

/**
 * A service to generate reports in html format.
 */
public class HtmlReportGenerator {
    /**
     * Generates a new report.
     *
     * @param reports a dataset for the report
     * @throws IOException if an I/O error occurred
     */
    public void generate(List<ReportEntry> reports) throws IOException {
        var templateEngine = getTemplateEngine();
        var context = new Context();
        context.setVariable("tasks", groupTasks(reports));
        context.setVariable("grades", calculateGrades(reports));
        context.setVariable("reportDate", LocalDateTime.now());

        var html = templateEngine.process("report", context);

        try (var writer = new FileWriter("reports.html")) {
            writer.write(html);
        }
    }

    private TemplateEngine getTemplateEngine() {
        var resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding("UTF-8");

        var templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(resolver);

        return templateEngine;
    }

    private Map<String, List<ReportEntry>> groupTasks(List<ReportEntry> reports) {
        return reports.stream()
            .collect(Collectors.groupingBy(ReportEntry::taskId));
    }

    private Map<String, Integer> calculateGrades(List<ReportEntry> reports) {
        return reports.stream()
            .collect(
                Collectors.groupingBy(
                    ReportEntry::studentName,
                    Collectors.summingInt(ReportEntry::grade)
                )
            );
    }
}
