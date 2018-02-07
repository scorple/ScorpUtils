package com.scorpius_enterprises.log2;

public class Driver
{
    public static void main(String args[])
    {
        Logger logger = new Logger("Driver");

        logger.log(new Log("log"));
    }
}
