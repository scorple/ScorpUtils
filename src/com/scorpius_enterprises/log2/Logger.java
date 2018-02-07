package com.scorpius_enterprises.log2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Logger
{
    private static final int _API_STACK_TRACE_DEPTH_ = 2;

    private String            component;
    private boolean           enabled;
    private ArrayList<String> whitelist;
    private ArrayList<String> blacklist;

    public Logger()
    {
        this(Thread.currentThread()
                   .getStackTrace()[_API_STACK_TRACE_DEPTH_].getClassName());
    }

    public Logger(final String _component_)
    {
        this.component = _component_;
        enabled = true;
        whitelist = new ArrayList<>();
        blacklist = new ArrayList<>();
    }

    public void log(Log log)
    {
        System.out.printf("[%s] %s [%s] %s",
                          component,
                          new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()),
                          "GENERAL",
                          log.getLogText());
    }
}
