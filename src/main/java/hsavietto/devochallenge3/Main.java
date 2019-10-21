package hsavietto.devochallenge3;

import org.apache.commons.cli.*;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * @author Helder Savietto (helder.savietto@gmail.com)
 */
public class Main {

    /**
     * Creates a parsed command line arguments object
     *
     * @param args arguments from the application call
     * @return a command line object, null if any mandatory argument is missing
     */
    private static CommandLine createCommandLineOptions(String[] args) {
        Options options = new Options();

        Option docsDirOption = new Option("d", "dir", true, "documents directory");
        docsDirOption.setRequired(true);
        options.addOption(docsDirOption);

        Option resultSizeOption = new Option("n", "resultSize", true, "result size");
        resultSizeOption.setRequired(true);
        options.addOption(resultSizeOption);

        Option periodOption = new Option("p", "period", true, "result period (in seconds)");
        periodOption.setRequired(true);
        options.addOption(periodOption);

        Option termsOption = new Option("t", "terms", true, "terms to be analysed (separated by spaces)");
        termsOption.setRequired(true);
        options.addOption(termsOption);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        try {
            return parser.parse(options, args);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            formatter.printHelp(" ", options);
            return null;
        }
    }

    /**
     * Gets the documents dir argument value
     *
     * @param cmd the command line object
     * @return a File object of the documents dir, null if not valid
     */
    private static File getDocsDir(CommandLine cmd) {
        String docsDirPath = cmd.getOptionValue("dir");
        File docsDir = new File(docsDirPath);

        if (!docsDir.exists() || !docsDir.isDirectory()) {
            System.err.println("\"" + docsDirPath + "\" is not a valid directory");
            return null;
        }

        return docsDir;
    }

    /**
     * Gets a integer command line option value
     *
     * @param cmd the command line object
     * @param optionName the option name
     * @return the integer value of the argument, null if not a valid integer
     */
    private static Integer getIntegerParameter(CommandLine cmd, String optionName) {
        String optionValue = cmd.getOptionValue(optionName);

        try {
            return Integer.parseInt(optionValue);
        } catch (NumberFormatException ex) {
            System.err.println("\"" + optionValue + "\" is not a valid number");
            return null;
        }
    }

    /**
     * Gets the result size argument value
     *
     * @param cmd the command line object
     * @return the result size, null if not a valid integer
     */
    private static Integer getResultSize(CommandLine cmd) {
        return getIntegerParameter(cmd, "resultSize");
    }

    /**
     * Gets the result period argument value
     *
     * @param cmd the command line object
     * @return the result period, null if not a valid integer
     */
    private static Integer getResultPeriod(CommandLine cmd) {
        return getIntegerParameter(cmd, "period");
    }

    /**
     * Gets the terms to have the df-idf values found in the documents
     *
     * @param cmd the command line object
     * @return a list with the terms
     */
    private static List<String> getTerms(CommandLine cmd) {
        String allTerms = cmd.getOptionValue("terms");
        List<String> terms = Arrays.asList(allTerms.split("\\s+"));

        if(terms.isEmpty()) {
            System.err.println("Unable to find usable terms");
        }

        return terms;
    }

    /**
     * Application entry point
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        CommandLine cmd = createCommandLineOptions(args);

        if (cmd == null) {
            System.exit(1);
        }

        File docsDir = getDocsDir(cmd);
        Integer resultSize = getResultSize(cmd);
        Integer resultPeriod = getResultPeriod(cmd);
        List<String> terms = getTerms(cmd);

        if (docsDir == null || resultSize == null || resultPeriod == null || terms.isEmpty()) {
            System.exit(1);
        }

        TfIdfApplication app = new TfIdfApplication(docsDir, resultSize, resultPeriod, terms);
        app.execute();
    }

}
