package hsavietto.devochallenge3;

import java.util.Collection;

/**
 * @author Helder Savietto (helder.savietto@gmail.com)
 */
public class TfIdfCalculator {

    /**
     * @param fullCorpus
     * @param documentCorpus
     * @param terms
     * @return
     */
    public double calculateTfIdf(
            Collection<DocumentCorpus> fullCorpus, DocumentCorpus documentCorpus, Collection<String> terms) {
        double tfIdf = 0.0;

        for (String term : terms) {
            double tf = calculateTermFrequency(documentCorpus, term);
            double idf = calculateTermInverseDocumentFrequency(fullCorpus, term);
            tfIdf += tf * idf;
        }

        return tfIdf;
    }

    /**
     * @param corpus
     * @param term
     * @return
     */
    private double calculateTermFrequency(DocumentCorpus corpus, String term) {
        int totalCount = corpus.countTotalTerms();

        if (totalCount == 0) {
            return 0.0;
        }

        return ((double) corpus.countTerm(term) / totalCount);
    }

    /**
     * @param fullCorpus
     * @param term
     * @return
     */
    private double calculateTermInverseDocumentFrequency(Collection<DocumentCorpus> fullCorpus, String term) {
        long appearances = fullCorpus.stream().filter(corpus -> corpus.countTerm(term) > 0).count();

        if (appearances == 0) {
            return 0.0;
        }

        return Math.log10((double) fullCorpus.size() / appearances);
    }

}
