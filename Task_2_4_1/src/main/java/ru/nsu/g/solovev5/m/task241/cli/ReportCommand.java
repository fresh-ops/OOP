package ru.nsu.g.solovev5.m.task241.cli;

import picocli.CommandLine;
import ru.nsu.g.solovev5.m.task241.core.services.HtmlReportGenerator;
import ru.nsu.g.solovev5.m.task241.core.services.ReportsRepository;

@CommandLine.Command(
    name = "report",
    description = "generates  an html report"
)
public class ReportCommand extends Command {
    private final ReportsRepository reportsRepository;
    private final HtmlReportGenerator htmlReportGenerator;

    public ReportCommand() {
        super();
        reportsRepository = new ReportsRepository(resolver);
        htmlReportGenerator = new HtmlReportGenerator();
    }

    @Override
    public Integer call() throws Exception {
        htmlReportGenerator.generate(
            reportsRepository.loadAll()
        );
        return 0;
    }
}
