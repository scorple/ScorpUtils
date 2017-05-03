package com.scorpius_enterprises.net2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * {@link InternetUtils com.scorpius_enterprises.net2.InternetUtils}
 *
 * @author Scorple
 * @since 2017-05-03
 */
public class InternetUtils
{
    public static String getPublicIP()
    {
        try
        {
            URL            awsCheckIpUrl = new URL("http://checkip.amazonaws.com");
            BufferedReader br            = new BufferedReader(new InputStreamReader(awsCheckIpUrl.openStream()));
            return br.readLine();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
