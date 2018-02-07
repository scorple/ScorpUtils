package com.scorpius_enterprises.log3;

import static com.scorpius_enterprises.log3.LogUtils.getClassName;
import static com.scorpius_enterprises.log3.LogUtils.getFileName;
import static com.scorpius_enterprises.log3.LogUtils.getLineNumber;
import static com.scorpius_enterprises.log3.LogUtils.getMethodName;
import static com.scorpius_enterprises.log3.LogUtils.getTimeStamp;

public class Logger
{
    private static final int STACK_TRACE_DEPTH = 2;

    private static final String LOG_TYPE_GENERAL = "GENERAL";
    private static final String LOG_TYPE_INFO    = "INFO";
    private static final String LOG_TYPE_DEBUG   = "DEBUG";
    private static final String LOG_TYPE_WARNING = "WARNING";
    private static final String LOG_TYPE_ERROR   = "ERROR";
    private static final String LOG_TYPE_TRACE   = "TRACE";

    private String  component;
    private boolean enabled;

    public Logger(final String component)
    {
        this.component = component;
        this.enabled = true;
    }

    public void enable()
    {
        enabled = true;
    }

    public void disable()
    {
        enabled = false;
    }

    private void postLog(StackTraceElement stackTraceElement,
                         String type,
                         String log)
    {
        if (enabled)
        {
            System.out.printf("%s [%-15S] <%-7S> %s: %d: %s: %s: %s\n",
                              getTimeStamp(),
                              component,
                              type,
                              getFileName(stackTraceElement),
                              getLineNumber(stackTraceElement),
                              getClassName(stackTraceElement),
                              getMethodName(stackTraceElement),
                              log);
        }
    }

    public void log(final String format,
                    final Object... params)
    {
        StackTraceElement stackTraceElement = Thread.currentThread()
                                                    .getStackTrace()[STACK_TRACE_DEPTH];

        String log = String.format(format,
                                   params);

        postLog(stackTraceElement,
                LOG_TYPE_GENERAL,
                log);
    }
}
