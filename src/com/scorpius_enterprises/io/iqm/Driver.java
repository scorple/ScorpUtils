package com.scorpius_enterprises.io.iqm;

import com.scorpius_enterprises.log.Logger;

/**
 * Created by rickm on 3/25/2017.
 */
public class Driver
{
    public static void main(String[] args)
    {
        Logger.init(0);
        Logger.registerTag(IqmLoader.LOG_TAG);

        IqmLoader.loadIBO("/test/test_arm.iqm");
    }
}
