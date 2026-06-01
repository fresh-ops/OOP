package ru.nsu.g.solovev5.m.task241;

import java.util.List;
import java.util.concurrent.Callable;
import picocli.CommandLine;
import ru.nsu.g.solovev5.m.task241.cli.BuildCommand;
import ru.nsu.g.solovev5.m.task241.cli.CleanCommand;
import ru.nsu.g.solovev5.m.task241.cli.CoverageCommand;
import ru.nsu.g.solovev5.m.task241.cli.EstimateCommand;
import ru.nsu.g.solovev5.m.task241.cli.FetchCommand;
import ru.nsu.g.solovev5.m.task241.cli.ReportCommand;
import ru.nsu.g.solovev5.m.task241.cli.StyleCommand;

/**
 * The application composition point.
 */
@CommandLine.Command(
    name = "checker",
    mixinStandardHelpOptions = true,
    description = "Helps to check OOP tasks. Run without parameters to make full check + report",
    subcommands = {
        FetchCommand.class,
        CleanCommand.class,
        CoverageCommand.class,
        StyleCommand.class,
        BuildCommand.class,
        EstimateCommand.class,
        ReportCommand.class
    },
    subcommandsRepeatable = true
)
public class Main implements Callable<Integer> {
    /**
     * The application entry point.
     *
     * @param args a command-line arguments
     */
    public static void main(String[] args) {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() {
        var commands = List.of(
            new FetchCommand(),
            new BuildCommand(),
            new StyleCommand(),
            new CoverageCommand(),
            new EstimateCommand(),
            new ReportCommand()
        );

        int exitCode = 0;
        for (var  command : commands) {
            exitCode = new CommandLine(command).execute();
            if (exitCode != 0) {
                break;
            }
        }
        System.exit(exitCode);

        return 0;
    }
}
