package wordcount;

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedHashMap;

import org.junit.Test;

import wordcount.textprocessor.TextStatistics;

public class AppTest 
{
    private static TextStatistics ts = new TextStatistics();

    private void applyCounts(final String filename)
    {
        ts.parseTextFromFile("src/test/resources/" + filename);
    }

    @Test
    public void test_runApp()
    {
        App.main(null);
        App.main(new String[] {"src/test/resources/adaptavist.txt", "src/test/resources/this_file_does_not_exist"});
    }

    @Test
    public void test_NonExistentFile()
    {
        assertTrue(ts.parseTextFromFile("this_file_does_not_exist.txt") == false);
        assertTrue(ts.getSortedCounts() == null);
    }

    @Test
    public void test_overloadedConstructor()
    {
        ts = new TextStatistics("src/test/resources/adaptavist.txt");
        assertTrue(ts.getSortedCounts() != null);
        assertTrue(ts.toString().length() > 0);
        assertTrue(ts.parseText(null) == false);
    }

    @Test
    public void test_EmptyFile()
    {
        assertTrue(ts.parseTextFromFile("empty-file.txt") == false);
        assertTrue(ts.getSortedCounts() == null);
    }

    @Test
    public void test_latinExampleText()
    {
        applyCounts("stockexample.txt");
        assertTrue(ts.getSortedCounts().get("et") == 6);
        assertTrue(ts.getSortedCounts().get("at") == 4);
        assertTrue(ts.getSortedCounts().get("eu") == 4);
        assertTrue(ts.getSortedCounts().get("lorem") == 1);
        assertTrue(ts.getSortedCounts().get("adaptavist") == null);
    }

    @Test
    public void test_shouldEvenWorkOnUnicodeSymbolText()
    {
        applyCounts("unicode.txt");
        assertTrue(ts.getSortedCounts().get("😀") == 1);
        assertTrue(ts.getSortedCounts().get("😁") == 1);
        assertTrue(ts.getSortedCounts().get("😃") == 1);
        assertTrue(ts.getSortedCounts().get("😄") == 1);
        assertTrue(ts.getSortedCounts().get("😅") == 1);
        assertTrue(ts.getSortedCounts().get("😆") == 1);
        assertTrue(ts.getSortedCounts().get("😇") == 1);
        assertTrue(ts.getSortedCounts().get("😉") == 1);
        assertTrue(ts.getSortedCounts().get("😊") == 1);
        assertTrue(ts.getSortedCounts().get("😋") == 1);
        assertTrue(ts.getSortedCounts().get("😌") == 1);
        assertTrue(ts.getSortedCounts().get("😍") == 1);
        assertTrue(ts.getSortedCounts().get("😎") == 1);
        assertTrue(ts.getSortedCounts().get("😏") == 1);
        assertTrue(ts.getSortedCounts().get("😐") == 1);
        assertTrue(ts.getSortedCounts().get("😑") == 1);
        assertTrue(ts.getSortedCounts().get("😒") == 1);
        assertTrue(ts.getSortedCounts().get("😓") == 1);
        assertTrue(ts.getSortedCounts().get("😔") == 1);
        assertTrue(ts.getSortedCounts().get("😕") == 1);
        assertTrue(ts.getSortedCounts().get("😖") == 1);
        assertTrue(ts.getSortedCounts().get("😈") == 10);
        assertTrue(ts.getSortedCounts().get("adaptavist") == null);
    }

    @Test
    public void test_AdaptavistText()
    {
        applyCounts("adaptavist.txt");
        assertTrue(ts.getSortedCounts().get("atlassian") == 8);
        assertTrue(ts.getSortedCounts().get("slack") == 5);
        assertTrue(ts.getSortedCounts().get("adaptavist") == 2);
        assertTrue(ts.getSortedCounts().get("microsoft") == null);
        assertTrue(ts.getSortedCounts().get("apple") == null);
    }

    @Test
    public void test_GutenbergText()
    {
        applyCounts("gutenberg.txt");
        assertTrue(ts.getSortedCounts().get("money") == 13);
        assertTrue(ts.getSortedCounts().get("caressed") == 1);
        assertTrue(ts.getSortedCounts().get("midst") == 1);
        assertTrue(ts.getSortedCounts().get("adaptavist") == null);
    }
}
