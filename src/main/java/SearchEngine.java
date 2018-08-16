import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.nio.file.Files;

public class SearchEngine {

    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("No directory given to index.");
        }

        Path path = Paths.get(args[0]);

        Scanner keyboard = new Scanner(System.in);

        while (true) {
            System.out.print("search> ");
            final String line = keyboard.nextLine();
            xx (line,path);
        }
    }

    private static void xx (String line, Path path) {

        try {
            DirectoryStream<Path> stream = Files.newDirectoryStream( path);
            for (Path p : stream) {
                countOfString(line,p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void countOfString(String searchString, Path fileName) {
        long count = 0;
        try {

            System.out.println(fileName.getFileName().toString());
            count = Files.lines(fileName)
                    .filter(s -> s.contains(searchString))
                    .count();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(count);
    }
}
