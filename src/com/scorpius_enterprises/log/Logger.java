package com.scorpius_enterprises.log;

import java.util.Arrays;

/**
 * {@link Logger com.scorpius_enterprises.logging.Logger}
 *
 * @author Scorple
 * @since 2017-03-11
 */
public class Logger
{
    private static final int STACK_TRACE_DEPTH = 2;

    private static final int LOG_LEVEL = 0;

    private static final String GENERAL = "GENERAL";
    private static final String INFO    = "INFO";
    private static final String DEBUG   = "DEBUG";

    private static final String SPLIT = ": ";

    private enum E_TAG
    {

    }

    private static final E_TAG[] ENABLED_TAGS = new E_TAG[]{

    };

    private static String getClassName(final StackTraceElement[] stackTrace)
    {
        String[] splitClassName =
            stackTrace[STACK_TRACE_DEPTH].getClassName().split("\\.");
        return splitClassName[splitClassName.length - 1];
    }

    private static String getMethodName(final StackTraceElement[] stackTrace)
    {
        return stackTrace[STACK_TRACE_DEPTH].getMethodName() + "()";
    }

    private static int getLineNumber(final StackTraceElement[] stackTrace)
    {
        return stackTrace[STACK_TRACE_DEPTH].getLineNumber();
    }

    private static void postLog(final StackTraceElement[] stackTrace, final String type, final String log)
    {
        System.out.println(type + SPLIT + getClassName(stackTrace) + SPLIT + getLineNumber(stackTrace) + SPLIT +
                           getMethodName(stackTrace) + ": " + log);
    }

    private static void processLog(final StackTraceElement[] stackTrace, final String type, final String log)
    {
        postLog(stackTrace, type, log);
    }

    private static void processLog(final StackTraceElement[] stackTrace,
                                   final String type,
                                   final String log,
                                   final int level)
    {
        if (LOG_LEVEL >= level)
        {
            postLog(stackTrace, type, log);
        }
    }

    private static void processLog(final StackTraceElement[] stackTrace,
                                   final String type,
                                   final String log,
                                   final E_TAG tag)
    {
        if (Arrays.stream(ENABLED_TAGS).anyMatch(e_tag -> e_tag == tag))
        {
            postLog(stackTrace, type + SPLIT + tag.toString(), log);
        }
    }

    private static void processLog(final StackTraceElement[] stackTrace,
                                   final String type,
                                   final String log,
                                   final E_TAG tag,
                                   final int level)
    {
        if (LOG_LEVEL >= level && Arrays.stream(ENABLED_TAGS).anyMatch(e_tag -> e_tag == tag))
        {
            postLog(stackTrace, type + SPLIT + tag.toString(), log);
        }
    }

    public static void log(String log)
    {
        processLog(Thread.currentThread().getStackTrace(), GENERAL, log);
    }

    public static void log(String log, int level)
    {
        processLog(Thread.currentThread().getStackTrace(), GENERAL, log, level);
    }

    public static void log(String log, E_TAG tag)
    {
        processLog(Thread.currentThread().getStackTrace(), GENERAL, log, tag);
    }

    public static void log(String log, E_TAG tag, int level)
    {
        processLog(Thread.currentThread().getStackTrace(), GENERAL, log, tag, level);
    }

    public static void logI(String log)
    {
        processLog(Thread.currentThread().getStackTrace(), INFO, log);
    }

    public static void logI(String log, int level)
    {
        processLog(Thread.currentThread().getStackTrace(), INFO, log, level);
    }

    public static void logI(String log, E_TAG tag)
    {
        processLog(Thread.currentThread().getStackTrace(), INFO, log, tag);
    }

    public static void logI(String log, E_TAG tag, int level)
    {
        processLog(Thread.currentThread().getStackTrace(), INFO, log, tag, level);
    }

    public static void logD(String log)
    {
        processLog(Thread.currentThread().getStackTrace(), DEBUG, log);
    }

    public static void logD(String log, int level)
    {
        processLog(Thread.currentThread().getStackTrace(), DEBUG, log, level);
    }

    public static void logD(String log, E_TAG tag)
    {
        processLog(Thread.currentThread().getStackTrace(), DEBUG, log, tag);
    }

    public static void logD(String log, E_TAG tag, int level)
    {
        processLog(Thread.currentThread().getStackTrace(), DEBUG, log, tag, level);
    }
}
