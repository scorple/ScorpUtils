package com.scorpius_enterprises.misc;

/**
 * Created by rickm on 3/25/2017.
 */
public class ArrayUtils
{
    public static void reverse(final byte[] array)
    {
        int head = 0;
        int tail = array.length - 1;

        while (head < tail)
        {
            byte temp = array[head];
            array[head++] = array[tail];
            array[tail--] = temp;
        }
    }

    public static void reverse(final byte[] array, final int offset, final int length)
        throws IndexOutOfBoundsException
    {
        int head = offset;
        int tail = offset + length - 1;

        while (head < tail)
        {
            byte temp = array[head];
            array[head++] = array[tail];
            array[tail--] = temp;
        }
    }
}
