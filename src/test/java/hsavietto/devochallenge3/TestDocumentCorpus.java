package hsavietto.devochallenge3;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Helder Savietto (helder.savietto@gmail.com)
 */
public class TestDocumentCorpus {

    @Test
    public void testLoremIpsum() {
        DocumentCorpus corpus = new DocumentCorpus();
        corpus.addTerm("lorem");
        corpus.addTerm("ipsum");
        corpus.addTerm("dolor");
        corpus.addTerm("sit");
        corpus.addTerm("amet");
        corpus.addTerm("dolor");
        corpus.addTerm("ipsum", 2);

        Assert.assertEquals(5, corpus.countIndividualTerms());
        Assert.assertEquals(8, corpus.countTotalTerms());
        Assert.assertEquals(1, corpus.countTerm("lorem"));
        Assert.assertEquals(3, corpus.countTerm("ipsum"));
        Assert.assertEquals(2, corpus.countTerm("dolor"));
        Assert.assertEquals(1, corpus.countTerm("sit"));
        Assert.assertEquals(1, corpus.countTerm("amet"));
        Assert.assertEquals(0, corpus.countTerm("absurdum"));
    }
}
