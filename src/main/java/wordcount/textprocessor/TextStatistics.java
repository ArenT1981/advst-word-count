package wordcount.textprocessor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class TextStatistics
{
    private LinkedHashMap<String, Integer> sortedCounts;
    // hard code a maximum line length limit 10,000 characters
    private final int LINELENGTHLIMIT = 10000;
    // hard code a maximum file size of 10,000,000 lines
    private final int FILELENGTHLIMIT = 10000000;

    public TextStatistics() {}

    // shortcut constructor overload to default reading from a file
    public TextStatistics(String filename)
    {
        parseTextFromFile(filename);
    }

    private FileReader openFile(String filename) throws FileNotFoundException
    {
         return new FileReader(filename);
    }

    private LinkedHashMap<String, Integer> sortWordCounts(LinkedHashMap<String, Integer> wordCounts)
    {
        // Use lambda/Java 8 streams sorted() to give us the word counts in descending order
        return wordCounts.entrySet().stream()
                          .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                          .collect(Collectors.toMap(Map.Entry::getKey,
                            Map.Entry::getValue,
                            (key, value) -> key, LinkedHashMap::new));
    }

    public boolean parseTextFromFile(final String path)
    {
        StringBuilder input = new StringBuilder();
        // try-with-resources will automatically cleanup afterwards
        try(BufferedReader br =  new BufferedReader(openFile(path)))
        {

            String line = "";
            int fl = 0;
            while((line = br.readLine()) != null &&
                   fl < FILELENGTHLIMIT &&
                   line.length() < LINELENGTHLIMIT)
            {
                ++fl;
                // Important: since we're chomping up newlines, ensure there
                // is a space character separating lines so tokenizer will work properly
                input.append(line + " ");
            }

            return parseText(input.toString());
        }
        catch(Exception e)
        {
            sortedCounts = null;
            return false;
        }
    }

    // Tokenise the text and get rid of all punctuation, and ignore title case
    public boolean parseText(final String input)
    {
        LinkedHashMap<String, Integer> wordCounts = new LinkedHashMap<>();
        try
        {
            // remove isolated hyphens from text, split altenatives on forward slash
            StringTokenizer st = new StringTokenizer(input.replaceAll(" - |\\/", " "));
            while (st.hasMoreTokens())
            {
                // We will DELIBERATELY ignore case. i.e. "The" == "the"; it is still the same word
                // Unicode punctuation class \p{P} helps us remove "'", """, or "," etc.
                // Use intersection with "NOT -" to preserve hyphenated words as one token, e.g. "peer-reviewed"
                String token = st.nextToken().toLowerCase().replaceAll("[[\\p{P}]&&[^-/]]", "");
                // If token doesn't exist, initialise as 1, otherwise increment by 1
                wordCounts.merge(token, 1, Integer::sum);
                //System.err.println("DEBUG -> Added token " + token);
            }

            sortedCounts =  sortWordCounts(wordCounts);
        }
        catch (NullPointerException ne)
        {
            sortedCounts = null;
            return false;
        }

        return true;
    }

    // Provide a nice simple console output by overriding toString method
    @Override
    public String toString()
    {
        StringBuilder resultSet = new StringBuilder("===== COUNTS ===== \n");

        for(Entry<String, Integer> countResult : sortedCounts.entrySet())
        {
             resultSet.append(countResult.getKey() + ": " + countResult.getValue() + "\n");
        }

        return resultSet.toString();
    }

    public LinkedHashMap<String, Integer> getSortedCounts()
    {
        return sortedCounts;
    }
}
