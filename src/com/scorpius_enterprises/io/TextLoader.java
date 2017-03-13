package com.scorpius_enterprises.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * {@link TextLoader com.scorpius_enterprises.loading.TextLoader}
 *
 * @author Scorple
 * @since 2017-03-11
 */
public class TextLoader
{
    /**
     * Get the complete text of a text file as a String.
     *
     * @param fileName String: The path to the plain text file to load.
     *
     * @return String: The full text of the loaded file as a String.
     */
    public static String loadTextFile(final String fileName)
    {
        StringBuilder res = new StringBuilder();
        try
        {
            InputStream    inputStream    = TextLoader.class.getClass().getResourceAsStream(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            while ((line = bufferedReader.readLine()) != null)
            {
                res.append(line).append('\n');
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return res.toString();
    }
}
