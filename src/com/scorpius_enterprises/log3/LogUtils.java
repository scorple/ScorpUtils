package com.scorpius_enterprises.log3;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogUtils
{
    public static String getTimeStamp()
    {
        return new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    }

    public static String getFileName(final StackTraceElement s)
    {
        return s.getFileName();
    }

    public static int getLineNumber(final StackTraceElement s)
    {
        return s.getLineNumber();
    }

    public static String getClassName(final StackTraceElement s)
    {
        final String[] cp = s.getClassName()
                             .split("\\.");
        return cp[cp.length - 1];
    }

    public static String getMethodName(final StackTraceElement s)
    {
        return String.format("%s()",
                             s.getMethodName());
    }
}
