package com.scorpius_enterprises.log2;

public class Log
{
    private static final int _API_STACK_TRACE_DEPTH_ = 2;

    private String logText = "ERROR: UNINITIALIZED LOG";

    public Log(final String _format_,
               final Object... _params_)
    {
        try
        {
            final StackTraceElement _stackTraceElement_ = Thread.currentThread()
                                                                .getStackTrace()
                [_API_STACK_TRACE_DEPTH_];

            final String _logIn_ = String.format(_format_,
                                                 _params_);

            logText = String.format("%s: %d: %s::%s: %s",
                                    getFileName(_stackTraceElement_),
                                    getLineNumber(_stackTraceElement_),
                                    getClassName(_stackTraceElement_),
                                    getMethodName(_stackTraceElement_),
                                    _logIn_);
        }
        catch (Exception x)
        {
            x.printStackTrace();
        }
    }

    private static String getFileName(final StackTraceElement _stackTraceElement_)
    {
        return _stackTraceElement_.getFileName();
    }

    private static int getLineNumber(final StackTraceElement _stackTraceElement_)
    {
        return _stackTraceElement_.getLineNumber();
    }

    private static String getClassName(final StackTraceElement _stackTraceElement_)
    {
        final String[] _classPath_ = _stackTraceElement_.getClassName()
                                                        .split("\\.");
        return _classPath_[_classPath_.length - 1];
    }

    private static String getMethodName(final StackTraceElement _stackTraceElement_)
    {
        return String.format("%s()",
                             _stackTraceElement_.getMethodName());
    }

    public String getLogText()
    {
        return logText;
    }
}
