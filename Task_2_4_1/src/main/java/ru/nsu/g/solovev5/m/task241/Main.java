package ru.nsu.g.solovev5.m.task241;

import java.util.concurrent.Callable;
import picocli.CommandLine;
import ru.nsu.g.solovev5.m.task241.cli.CleanCommand;
import ru.nsu.g.solovev5.m.task241.cli.FetchCommand;
import ru.nsu.g.solovev5.m.task241.cli.StyleCommand;

@CommandLine.Command(
    name = "checker",
    mixinStandardHelpOptions = true,
    description = "Helps to check OOP tasks",
    subcommands = {
        FetchCommand.class,
        CleanCommand.class,
        StyleCommand.class
    }
)
public class Main implements Callable<Integer> {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() {
        System.out.println("Use -h or --help to get help");
        return 0;
    }
}
