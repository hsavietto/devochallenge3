package hsavietto.devochallenge3;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * @author Helder Savietto (helder.savietto@gmail.com)
 */
public class TestCorpusBuilder {

    @Test
    public void testLoremIpsum() throws IOException {
        String loremIpsum = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor.\n" +
                "Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.\n" +
                "Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim.\n" +
                "Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu.\n" +
                "In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium.\n" +
                "Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus.\n" +
                "Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim.\n" +
                "Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus.\n" +
                "Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet.\n" +
                "Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui.";
        CorpusBuilder builder = new CorpusBuilder();
        DocumentCorpus corpus = builder.createCorpus(new ByteArrayInputStream(loremIpsum.getBytes()));
        Assert.assertEquals(84, corpus.countIndividualTerms());
        Assert.assertEquals(124, corpus.countTotalTerms());
        Assert.assertEquals(5, corpus.countTerm("aenean"));
        Assert.assertEquals(3, corpus.countTerm("nisi"));
        Assert.assertEquals(3, corpus.countTerm("ultricies"));
    }

}
