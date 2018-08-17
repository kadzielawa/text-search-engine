import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class SearchThread implements Callable<Float> {

    private final static Logger logger = Logger.getLogger(SearchEngine.class.getName());

    public Path filename;
    private Set<String> desirableWords;

    public SearchThread(Path filename,Set<String> desirableWords){
        this.filename = filename;
        this.desirableWords = desirableWords;
    }

    @Override
    public Float call() {
        try {

            Set<String> desirableWordsToFind = new HashSet<>(desirableWords);

            try (Stream<String> lines = Files.lines(filename, StandardCharsets.UTF_8)) {
                lines.forEach((k) -> {
                    for (String word : k.split("\\W")) {
                        if (word.length() > 1) {
                            desirableWordsToFind.remove(word);
                        }
                    }
                });

                return  ((float)  (desirableWords.size() - desirableWordsToFind.size()) / desirableWords.size()) * 100;
            }

        } catch (IOException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return null;
    }
}
