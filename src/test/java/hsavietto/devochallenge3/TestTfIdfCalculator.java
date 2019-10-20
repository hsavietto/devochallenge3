package hsavietto.devochallenge3;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @uthor Helder Savietto (helder.savietto@gmail.com)
 */
public class TestTfIdfCalculator {

    private static final double DELTA = 1.0e-3;

    private DocumentCorpus createFirstCorpus() {
        DocumentCorpus corpus = new DocumentCorpus();
        corpus.addTerm("this");
        corpus.addTerm("is");
        corpus.addTerm("a", 2);
        corpus.addTerm("sample");
        return corpus;
    }

    private DocumentCorpus createSecondCorpus() {
        DocumentCorpus corpus = new DocumentCorpus();
        corpus.addTerm("this");
        corpus.addTerm("is");
        corpus.addTerm("another", 2);
        corpus.addTerm("example", 3);
        return corpus;
    }

    @Test
    public void testTfIdfCalculator() {
        DocumentCorpus firstCorpus = createFirstCorpus();
        DocumentCorpus secondCorpus = createSecondCorpus();
        List<DocumentCorpus> fullCorpus = Arrays.asList(firstCorpus, secondCorpus);
        TfIdfCalculator calculator = new TfIdfCalculator();

        double thisTfIdf1 = calculator.calculateTfIdf(fullCorpus, firstCorpus, Collections.singletonList("this"));
        double thisTfIdf2 = calculator.calculateTfIdf(fullCorpus, secondCorpus, Collections.singletonList("this"));
        double absurdumTfIdf1 = calculator.calculateTfIdf(fullCorpus, firstCorpus, Collections.singletonList("absurdum"));
        double absurdumTfIdf2 = calculator.calculateTfIdf(fullCorpus, secondCorpus, Collections.singletonList("absurdum"));
        double exampleTfIdf1 = calculator.calculateTfIdf(fullCorpus, firstCorpus, Collections.singletonList("example"));
        double exampleTfIdf2 = calculator.calculateTfIdf(fullCorpus, secondCorpus, Collections.singletonList("example"));

        Assert.assertEquals(0.0, thisTfIdf1, DELTA);
        Assert.assertEquals(0.0, thisTfIdf2, DELTA);
        Assert.assertEquals(0.0, absurdumTfIdf1, DELTA);
        Assert.assertEquals(0.0, absurdumTfIdf2, DELTA);
        Assert.assertEquals(0.0, exampleTfIdf1, DELTA);
        Assert.assertEquals(0.129, exampleTfIdf2, DELTA);

        List<String> composedTerms = Arrays.asList("this", "another", "example");
        double composedTfIdf1 = calculator.calculateTfIdf(fullCorpus, firstCorpus, composedTerms);
        double composedTfIdf2 = calculator.calculateTfIdf(fullCorpus, secondCorpus, composedTerms);

        Assert.assertEquals(0.0, composedTfIdf1, DELTA);
        Assert.assertEquals(0.215, composedTfIdf2, DELTA);
    }
}
