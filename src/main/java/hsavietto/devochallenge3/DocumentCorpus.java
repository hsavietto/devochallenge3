package hsavietto.devochallenge3;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Helder Savietto (helder.savietto@gmail.com)
 */
public class DocumentCorpus {

    private final Map<String, Integer> termsCount;

    /**
     * Constructor
     */
    public DocumentCorpus() {
        termsCount = new HashMap<>();
    }

    /**
     * @param term
     */
    public void addTerm(String term) {
        termsCount.merge(term, 1, Integer::sum);
    }

    /**
     * @param term
     * @param count
     */
    public void addTerm(String term, int count) {
        termsCount.merge(term, count, Integer::sum);
    }

    /**
     * @param word
     * @return
     */
    public int countTerm(String word) {
        return termsCount.getOrDefault(word, 0);
    }

    /**
     * @return
     */
    public int countTotalTerms() {
        return termsCount.values().stream().mapToInt(Integer::intValue).sum();
    }

    /**
     * @return
     */
    public int countIndividualTerms() {
        return termsCount.size();
    }

}
