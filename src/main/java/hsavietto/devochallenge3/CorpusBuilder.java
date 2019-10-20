package hsavietto.devochallenge3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Helder Savietto (helder.savietto@gmail.com)
 */
public class CorpusBuilder {

    /**
     * Creates a corpus from a input stream
     *
     * @param in the input stream with the text data
     * @return an instance of DocumentCorpus with the corpus of the input
     * @throws IOException
     */
    public DocumentCorpus createCorpus(InputStream in) throws IOException {
        DocumentCorpus corpus = new DocumentCorpus();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.trim().split("\\s+");

                for (String token : tokens) {
                    corpus.addTerm(token.toLowerCase().replaceAll("[^A-Za-z0-9\\-]", ""));
                }
            }
        }

        return corpus;
    }
}
