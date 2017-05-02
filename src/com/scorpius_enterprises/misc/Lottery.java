package com.scorpius_enterprises.misc;

import com.sun.istack.internal.Nullable;

import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * Created by rickm on 4/19/2017.
 */
public class Lottery
{
    @Nullable
    public static String performLottery(final HashMap<String, Integer> values)
    {
        HashMap<String, Integer> adjustedValues = new HashMap<>();
        final Integer[]          totalTickets   = {0};

        values.keySet()
              .stream()
              .parallel()
              .forEachOrdered(key ->
                              {
                                  totalTickets[0] += values.get(key);
                                  adjustedValues.put(key, totalTickets[0]);
                              });

        int ticket = (int) (Math.random() * totalTickets[0]);

        try
        {
            //noinspection ConstantConditions
            return adjustedValues.keySet()
                                 .stream()
                                 .parallel()
                                 .filter(key ->
                                             ticket <= adjustedValues.get(key) - values.get(key) &&
                                             ticket < adjustedValues.get(key))
                                 .findFirst().get();
        }
        catch (NoSuchElementException x)
        {
            return null;
        }
    }

    @Nullable
    public static String performLottery(final String[] keys, final int[] values)
    {
        if (keys.length != values.length)
        {
            return null;
        }

        int   totalTickets = 0;
        int[] offsetValues = new int[values.length];

        for (int i = 0; i < keys.length; ++i)
        {
            totalTickets += values[i];
            offsetValues[i] = totalTickets;
        }

        int ticket = (int) (Math.random() * totalTickets);

        for (int i = 0; i < keys.length; ++i)
        {
            if (ticket < offsetValues[i])
            {
                return keys[i];
            }
        }

        return null;
    }
}
