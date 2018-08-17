import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SearchEngine {

    private final static Logger logger = Logger.getLogger(SearchEngine.class.getName());
    public static Set<String> desirableWords;

    public static void process(String phrase, Path path) {

        HashMap<String,Float> fileResults = new HashMap<String,Float>();
        ExecutorService service = Executors.newFixedThreadPool(2);
        setPath(phrase);

        try {
            DirectoryStream<Path> stream = Files.newDirectoryStream(path);
            for (Path fileName : stream) {
                Future<Float> result = service.submit( new SearchThread(fileName,desirableWords));
                fileResults.put(fileName.getFileName().toString(), result.get());
                }
        } catch (IOException | InterruptedException | ExecutionException e) {
            logger.log(Level.WARNING, e.getMessage());
        }

        showResults(fileResults);
    }

    private static void setPath(String phrase){
        desirableWords  = new HashSet<>();
        for (String str : phrase.split("\\W")) {
            if (str.length() > 1) {
                desirableWords.add(str);
            }
        }
    }

    private static void showResults(HashMap<String,Float> fileResults){

        List<Map.Entry> a = new ArrayList<Map.Entry>(fileResults.entrySet());
        Collections.sort(a,
                (Comparator) (o1, o2) -> {
                    Map.Entry e1 = (Map.Entry) o1;
                    Map.Entry e2 = (Map.Entry) o2;
                    return ((Comparable) e2.getValue()).compareTo(e1.getValue());
                });
        for (Map.Entry e : a) {
            System.out.println(e.getKey() + ": " + e.getValue() + "%") ;
        }
    }

}
