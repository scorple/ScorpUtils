package com.scorpius_enterprises.io.iqm;

/**
 * Created by rickm on 3/25/2017.
 */
public class Driver
{
    public static void main(String[] args)
    {
        IqmLoader.loadIBO("/test/test_arm.iqm", null, null, null, null, null);
    }
}
