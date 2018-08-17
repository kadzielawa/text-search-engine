import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main {

    private final static Logger logger = Logger.getLogger(SearchEngine.class.getName());

    public static void main(String[] args) {
        initializeLogger();
        argumentChecker(args);
        Path path = Paths.get(args[0]);
        Scanner keyboard = new Scanner(System.in);

        while (true) {
            System.out.print("search> ");
            final String line = keyboard.nextLine();
            SearchEngine.process(line, path);
        }
    }

    private static void argumentChecker(String[] args) {

        try {
            if (args.length > 0) {
                if (args[0].isEmpty()) {
                    logger.log(Level.WARNING, "Arguments should not be null! Please pass valid arguments!");
                    System.exit(-1);
                }

                File directoryToCheck = new File(args[0]);
                if (!directoryToCheck.exists() || !directoryToCheck.isDirectory()) {
                    throw new NoSuchFileException("passed argument should be a path. This is not directory or doesn't exists!");
                }
            } else {
                throw new Exception("please pass valid path for directory eg. '/foo/bar'");
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    private static void initializeLogger() {
        try {
            FileHandler fh = new FileHandler("text-search-engine.log");
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
        } catch (IOException e) {
            logger.log(Level.WARNING, "failed to open log file", e.getMessage());
        }
    }
}
