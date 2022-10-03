package wordcount;

import wordcount.textprocessor.TextStatistics;

public class App
{
    private static void runDemo()
    {
        System.out
                .println("No command line argument[s] detected, running demo.");
        System.out.println("\n\n\nDEMO\n\n\n");
        TextStatistics ts = new TextStatistics();
        ts.parseText("The cat sat on the dog's mat and the dog cried. "
                   + "The cat groomed its whiskers, declared 'This mat is mine, for I am a cat, and as a cat, I shall cat, "
                   + "and do what thou cat shall be the whole of the law!'. The cat then purred, curled up, "
                   + "and fell blissfully asleep. The dog lay down on the conrete, moribund.");
        System.out.println("Input string:\n"
                         + "The cat sat on the dog's mat and the dog cried. "
                         + "The cat groomed its whiskers, declared \"This mat is mine, for I am a cat, and as a cat, I shall cat, "
                         + "and do what thou cat shall be the whole of the law!\". The cat then purred, curled up, "
                         + "and fell blissfully asleep. The dog lay down on the conrete, moribund.\n");
        System.out.println(ts);
        System.out.println("\n===========\n");
        ts.parseTextFromFile("src/test/resources/stockexample.txt");
        System.out.println("Input file src/test/resources/stockexample.txt\n");
        System.out.println(ts);
        System.out.println("\n===========\n");
        System.out.println("Input file src/test/resources/adaptavist.txt\n");
        ts.parseTextFromFile("src/test/resources/adaptavist.txt");
        System.out.println(ts);
    }

    public static void main(String[] args)
    {
        if (args == null || args.length == 0)
        {
            runDemo();
        }
        else
        {
            for (int i = 0; i < args.length; i++)
            {
                try
                {
                    System.out.println("Word count statistics for file: " + args[i]);
                    System.out.println(new TextStatistics(args[i]));
                } 
                catch(Exception e)
                {
                    System.err.println("Problem opening file \"" + args[i] + "\". Does the file exist? Skipping file." +
                                       " \n(Error: " + e.getMessage() + ").");
                }
            }
        }
    }
}
