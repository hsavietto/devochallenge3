package hsavietto.devochallenge3;

import javafx.util.Pair;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * The application of tf-idf file scanning
 *
 * @author Helder Savietto (helder.savietto@gmail.com)
 */
class TfIdfApplication {

    private final File docsDir;
    private final Integer resultSize;
    private final Integer resultPeriod;
    private final List<String> terms;
    private final Map<File, DocumentCorpus> fullCorpus = new HashMap<>();
    private final Set<File> excludedFiles = new HashSet<>();
    private final CorpusBuilder corpusBuilder = new CorpusBuilder();
    private final TfIdfCalculator calculator = new TfIdfCalculator();
    private final List<Pair<File, Double>> currentRank = new ArrayList<>();

    /**
     * Constructor
     *
     * @param docsDir      directory where the docs are to be scanned
     * @param resultSize   the maximum size of the result
     * @param resultPeriod the period in seconds between scans
     * @param terms        the terms to be evaluated in the documents
     */
    TfIdfApplication(File docsDir, Integer resultSize, Integer resultPeriod, List<String> terms) {
        this.docsDir = docsDir;
        this.resultSize = resultSize;
        this.resultPeriod = resultPeriod;
        this.terms = terms;
    }

    /**
     * Scans for new documents and updates the internal files map
     *
     * @returny whether there's a change since last scan
     */
    private boolean scanNewDocuments() {
        File[] files = docsDir.listFiles();
        boolean changed = false;

        for (File file : files) {
            if (file.isFile() && !fullCorpus.containsKey(file) && !excludedFiles.contains(file)) {
                try {
                    DocumentCorpus fileCorpus = corpusBuilder.createCorpus(new FileInputStream(file));
                    fullCorpus.put(file, fileCorpus);
                    changed = true;
                } catch (IOException e) {
                    System.out.println("Error processing corpus of file \"" + file.getAbsolutePath() + "\"");
                    excludedFiles.add(file);
                }
            }
        }

        return changed;
    }

    /**
     * Builds a rank of the tf-idf values of the terms in the documents found so far
     */
    private void buildRank() {
        currentRank.clear();

        for (Map.Entry<File, DocumentCorpus> entry : fullCorpus.entrySet()) {
            double tfIdf = calculator.calculateTfIdf(fullCorpus.values(), entry.getValue(), terms);
            currentRank.add(new Pair<>(entry.getKey(), tfIdf));
        }

        currentRank.sort(Comparator.comparingDouble(Pair::getValue));
    }

    /**
     * Prints the rank of the
     */
    private void printRank() {
        if (currentRank.isEmpty()) {
            System.out.println("Empty rank");
            return;
        }

        System.out.println("tf-idf rank:");

        for (Pair<File, Double> rankEntry : currentRank) {
            System.out.println(String.format("%s\t%s", rankEntry.getKey().getName(), rankEntry.getValue()));
        }

        System.out.println();
    }

    /**
     * The main loop of the application
     */
    void execute() {
        long lastExecution = -1;

        while (true) {
            long elapsed = System.currentTimeMillis() - lastExecution;
            long waitTime = this.resultPeriod * 1000 - elapsed;

            if (waitTime > 0) {
                try {
                    Thread.sleep(waitTime);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            lastExecution = System.currentTimeMillis();
            boolean changed = scanNewDocuments();

            if (changed) {
                buildRank();
            }

            printRank();
        }
    }
}
