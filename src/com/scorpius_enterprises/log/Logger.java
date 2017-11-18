package com.scorpius_enterprises.log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * {@link Logger com.scorpius_enterprises.log.Logger}
 *
 * @author Scorple
 * @since 2017-03-11
 */
public class Logger
{
    private static Logger instance;

    private static int STACK_TRACE_DEPTH = 2;

    private static       int               LOG_LEVEL       = 0;
    private static final ArrayList<String> REGISTERED_TAGS = new ArrayList<>();

    public enum E_TYPE
    {
        GENERAL,
        INFO,
        DEBUG,
        WARNING,
        ERROR
    }

    private static final ArrayList<E_TYPE> DISABLED_TYPES = new ArrayList<>();

    private static final String SPLIT = ": ";

    private Logger()
    {

    }

    private static void checkInstance()
    {
        if (instance == null)
        {
            instance = new Logger();

            STACK_TRACE_DEPTH = 3;

            processLog(Thread.currentThread().getStackTrace(),
                       E_TYPE.WARNING,
                       "attempt to log or configure without initializing, created Logger instance with LOG_LEVEL 0");

            STACK_TRACE_DEPTH = 2;
        }
    }

    /**
     * Must be called to ensure there is an instance of this otherwise static
     * class such that {@code LOG_LEVEL} and {@code REGISTERED_TAGS} data will
     * not be destroyed.
     *
     * @param LOG_LEVEL int: The maximum level of logs accompanied by a {@code
     *                  level} parameter to display.
     */
    public static void init(final int LOG_LEVEL)
    {
        if (instance == null)
        {
            instance = new Logger();
        }

        Logger.LOG_LEVEL = LOG_LEVEL;
    }

    /**
     * May be called to enable a single tag for logging. Log method calls
     * including a tag parameter will only be displayed if that tag is in the
     * list of registered tags.
     *
     * @param tag String: The tag to register.
     */
    public static void registerTag(String tag)
    {
        checkInstance();

        if (!REGISTERED_TAGS.contains(tag))
        {
            Logger.REGISTERED_TAGS.add(tag);
        }
    }

    /**
     * May be called to enable a list of tags for logging. Log method calls
     * including a tag parameter will only be displayed if that tag is in the
     * list of registered tags.
     *
     * @param tags String[]: The list of tags to register.
     */
    public static void registerTags(String[] tags)
    {
        checkInstance();

        Arrays.stream(tags).parallel().forEachOrdered(tag ->
                                                      {
                                                          if (!REGISTERED_TAGS.contains(tag))
                                                          {
                                                              Logger.REGISTERED_TAGS.add(tag);
                                                          }
                                                      });
    }

    /**
     * May be called to enable a list of tags for logging. Log method calls
     * including a tag parameter will only be displayed if that tag is in the
     * list of registered tags.
     *
     * @param tags ArrayList: The list of tags to register.
     */
    public static void registerTags(ArrayList<String> tags)
    {
        checkInstance();

        tags.stream().parallel().forEachOrdered(tag ->
                                                {
                                                    if (!REGISTERED_TAGS.contains(tag))
                                                    {
                                                        Logger.REGISTERED_TAGS.add(tag);
                                                    }
                                                });
    }

    /**
     * May be called to disable a single tag for logging. Log method calls
     * including a tag parameter will only be displayed if that tag is in the
     * list of registered tags.
     *
     * @param tag String: The tag to deregister.
     */
    public static void deregisterTag(String tag)
    {
        checkInstance();

        if (REGISTERED_TAGS.contains(tag))
        {
            Logger.REGISTERED_TAGS.remove(tag);
        }
    }

    /**
     * May be called to disable a list of tags for logging. Log method calls
     * including a tag parameter will only be displayed if that tag is in the
     * list of registered tags.
     *
     * @param tags String[]: The list of tags to deregister.
     */
    public static void deregisterTags(String[] tags)
    {
        checkInstance();

        Arrays.stream(tags).parallel().forEachOrdered(tag ->
                                                      {
                                                          if (REGISTERED_TAGS.contains(tag))
                                                          {
                                                              Logger.REGISTERED_TAGS.remove(tag);
                                                          }
                                                      });
    }

    /**
     * May be called to disable a list of tags for logging. Log method calls
     * including a tag parameter will only be displayed if that tag is in the
     * list of registered tags.
     *
     * @param tags ArrayList: The list of tags to deregister.
     */
    public static void deregisterTags(ArrayList<String> tags)
    {
        checkInstance();

        tags.stream().parallel().forEachOrdered(tag ->
                                                {
                                                    if (REGISTERED_TAGS.contains(tag))
                                                    {
                                                        Logger.REGISTERED_TAGS.remove(tag);
                                                    }
                                                });
    }

    /**
     * May be called to prevent a single log type from being displayed. All log
     * types will display by default.
     *
     * @param type E_TYPE: The type of log to prevent from displaying.
     */
    public static void disableType(E_TYPE type)
    {
        checkInstance();

        if (!DISABLED_TYPES.contains(type))
        {
            DISABLED_TYPES.add(type);
        }
    }

    /**
     * May be called to prevent any from a list of log types from being
     * displayed. All log types will display by default.
     *
     * @param types E_TYPE[]: The list of log types to prevent from displaying.
     */
    public static void disableTypes(E_TYPE[] types)
    {
        checkInstance();

        Arrays.stream(types).parallel().forEachOrdered(type ->
                                                       {
                                                           if (!DISABLED_TYPES.contains(type))
                                                           {
                                                               DISABLED_TYPES.add(type);
                                                           }
                                                       });
    }

    /**
     * May be called to prevent any from a list of log types from being
     * displayed. All log types will display by default.
     *
     * @param types ArrayList: The list of log types to prevent from
     *              displaying.
     */
    public static void disableTypes(ArrayList<E_TYPE> types)
    {
        checkInstance();

        types.stream().parallel().forEachOrdered(type ->
                                                 {
                                                     if (!DISABLED_TYPES.contains(type))
                                                     {
                                                         DISABLED_TYPES.add(type);
                                                     }
                                                 });
    }

    /**
     * May be called to undo preventing a log type from being displayed. All
     * log types are enabled by default.
     *
     * @param type E_TYPE: The log type to allow to display.
     */
    public static void enableType(E_TYPE type)
    {
        checkInstance();

        if (DISABLED_TYPES.contains(type))
        {
            DISABLED_TYPES.remove(type);
        }
    }

    /**
     * May be called to undo preventing any from a list of log types from being
     * displayed. All log types are enabled by default.
     *
     * @param types E_TYPE[]: The list of log types to allow to display.
     */
    public static void enableTypes(E_TYPE[] types)
    {
        checkInstance();

        Arrays.stream(types).parallel().forEachOrdered(type ->
                                                       {
                                                           if (DISABLED_TYPES.contains(type))
                                                           {
                                                               DISABLED_TYPES.remove(type);
                                                           }
                                                       });
    }

    /**
     * May be called to undo preventing any from a list of log types from being
     * displayed. All log types are enabled by default.
     *
     * @param types ArrayList: The list of log types to allow to display.
     */
    public static void enableTypes(ArrayList<E_TYPE> types)
    {
        checkInstance();

        types.stream().parallel().forEachOrdered(type ->
                                                 {
                                                     if (DISABLED_TYPES.contains(type))
                                                     {
                                                         DISABLED_TYPES.remove(type);
                                                     }
                                                 });
    }

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

    private static void postLog(final StackTraceElement[] stackTrace,
                                final String type,
                                final String log)
    {
        System.out.println(
            new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()) +
            SPLIT +
            type +
            SPLIT +
            getClassName(stackTrace) +
            SPLIT +
            getLineNumber(stackTrace) +
            SPLIT +
            getMethodName(stackTrace) +
            SPLIT +
            log);
    }

    private static void processLog(final StackTraceElement[] stackTrace,
                                   final E_TYPE type,
                                   final String log)
    {
        if (!DISABLED_TYPES.contains(type))
        {
            postLog(stackTrace,
                    type.toString(),
                    log);
        }
    }

    private static void processLog(final StackTraceElement[] stackTrace,
                                   final E_TYPE type,
                                   final String log,
                                   final int level)
    {
        if (!DISABLED_TYPES.contains(type) && LOG_LEVEL >= level)
        {
            postLog(stackTrace,
                    type.toString() + SPLIT + level,
                    log);
        }
    }

    private static void processLog(final StackTraceElement[] stackTrace,
                                   final E_TYPE type,
                                   final String log,
                                   final String tag)
    {
        if (!DISABLED_TYPES.contains(type) &&
            REGISTERED_TAGS.stream().parallel().anyMatch(e_tag -> e_tag.equals(tag)))
        {
            postLog(stackTrace,
                    type.toString() + SPLIT + tag,
                    log);
        }
    }

    private static void processLog(final StackTraceElement[] stackTrace,
                                   final E_TYPE type,
                                   final String log,
                                   final String tag,
                                   final int level)
    {
        if (!DISABLED_TYPES.contains(type) && LOG_LEVEL >= level &&
            REGISTERED_TAGS.stream().parallel().anyMatch(e_tag -> e_tag.equals(tag)))
        {
            postLog(stackTrace,
                    type.toString() + SPLIT + level + SPLIT + tag,
                    log);
        }
    }

    /**
     * Log a {@code GENERAL} message with no given purpose.
     * <p>
     * This log has no accompanying parameters and will always be displayed
     * unless {@code GENERAL} logs are disabled.
     *
     * @param log String: The log message to print out.
     */
    public static void log(final String log)
    {
        checkInstance();

        processLog(Thread.currentThread().getStackTrace(),
                   E_TYPE.GENERAL,
                   log);
    }

    /**
     * Log a {@code GENERAL} message with no given purpose.
     * <p>
     * This log is accompanied by a log {@code level} parameter and will only
     * be displayed if that log level is less than or equal to the {@link
     * Logger Logger's} current {@code LOG_LEVEL}.
     *
     * @param log   String: The log message to print out.
     * @param level int: The level of this log.
     *
     * @see #init(int)
     */
    public static void log(final String log,
                           final int level)
    {
        checkInstance();

        processLog(Thread.currentThread().getStackTrace(),
                   E_TYPE.GENERAL,
                   log,
                   level);
    }

    /**
     * Log a {@code GENERAL} message with no given purpose.
     * <p>
     * This log is accompanied by a log {@code tag} parameter and will only be
     * displayed if that log tag has been registered with the {@link Logger}.
     *
     * @param log String: The log message to print out.
     * @param tag String: The tag which must be registered for this log to be
     *            displayed.
     *
     * @see #registerTag(String)
     * @see #registerTags(String[])
     * @see #registerTags(ArrayList)
     */
    public static void log(final String log,
                           final String tag)
    {
        checkInstance();

        processLog(Thread.currentThread().getStackTrace(),
                   E_TYPE.GENERAL,
                   log,
                   tag);
    }

    /**
     * Log a {@code GENERAL} message with no given purpose.
     * <p>
     * This log is accompanied by a log {@code tag} parameter and will only be
     * displayed if that log tag has been registered with the {@link Logger}.
     * <p>
     * This log is accompanied by a log {@code level} parameter and will only
     * be displayed if that log level is less than or equal to the {@link
     * Logger Logger's} current {@code LOG_LEVEL}.
     *
     * @param log   String: The log message to print out.
     * @param tag   String: The tag which must be registered for this log to be
     *              displayed.
     * @param level int: The level of this log.
     *
     * @see #registerTag(String)
     * @see #registerTags(String[])
     * @see #registerTags(ArrayList)
     * @see #init(int)
     */
    public static void log(final String log,
                           final String tag,
                           final int level)
    {
        checkInstance();

        processLog(Thread.currentThread().getStackTrace(),
                   E_TYPE.GENERAL,
                   log,
                   tag,
                   level);
    }

    /**
     * Log an {@code INFO} message to provide information about program state
     * which may be helpful in understanding program progression and status.
     * <p>
     * This log has no accompanying parameters and will always be displayed
     * unless {@code INFO} logs are disabled.
     *
     * @param log String: The log message to print out.
     */
    public static void logI(final String log)
    {
        checkInstance();

        processLog(Thread.currentThread().getStackTrace(),
                   E_TYPE.INFO,
                   log);
    }

    /**
     * Log an {@code INFO} message to provide information about program state
     * which may be helpful in understanding program progression and status.
     * <p>
     * This log is accompanied by a log {@code level} parameter and will only
     * be displayed if that log level is less than or equal to the {@link
     * Logger Logger's} current {@code LOG_LEVEL}.
     *
     * @param log   String: The log message to print out.
     * @param level int: The level of this log.
     *
     * @see #init(int)
     */
    public static void logI(final String log,
                            final int level)
    {
        checkInstance();

        processLog(Thread.currentThread().getStackTrace(),
                   E_TYPE.INFO,
                   log,
                   level);
    }

    /**
     * Log an {@code INFO} message to provide information about program state
     * which may be helpful in understanding program progression and status.
     * <p>
     * This log is accompanied by a log {@code tag} parameter and will only be
     * displayed if that log tag has been registered with the {@link Logger}.
     *
     * @param log String: The log message to print out.
     * @param tag String: The tag which must be registered for this log to be
     *            displayed.
     *
     * @see #registerTag(String)
     * @see #registerTags(String[])
     * @see #registerTags(ArrayList)
     */
    public static void logI(final String log,
                            final String tag)
    {
        checkInstance();

        processLog(Thread.currentThread().getStackTrace(),
                   E_TYPE.INFO,
                   log,
                   tag);
    }

    /**
     * Log an {@code INFO} message to provide information about program state
     * which may be helpful in understanding program progression and status.
     * <p>
     * This log is accompanied by a log {@code tag} parameter and will only be
     * displayed if that log tag has been registered with the {@link Logger}.
     * <p>
     * This log is accompanied by a log {@code level} parameter and will only
     * be displayed if that log level is less than or equal to the {@link
     * Logger Logger's} current {@code LOG_LEVEL}.
     *
     * @param log   String: The log message to print out.
     * @param tag   String: The tag which must be registered for this log to be
     *              displayed.
     * @param level int: The level of this log.
     *
     * @see #registerTag(String)
     * @see #registerTags(String[])
     * @see #registerTags(ArrayList)
     * @see #init(int)
     */
    public static void logI(final String log,
                            final String tag,
                            final int level)
    {
        checkInstance();

        processLog(Thread.currentThread().getStackTrace(),
                   E_TYPE.INFO,
                   log,
                   tag,
                   level);
    }

    /**
     * Log a {@code DEBUG} message to provide information about program state
     * which may be helpful in diagnosing problems.
     * <p>
     * This log has no accompanying parameters and will always be displayed
     * unless {@code DEBUG} logs are disabled.
     *
     * @param log String: The log message to print out.
     */
    public static void logD(final String log)
    {
        checkInstance();

        processLog(Thread.currentThread().getStackTrace(),
                   E_TYPE.DEBUG,
                   log);
    }

    /**
     * Log a {@code DEBUG} message to provide information about program state
     * which may be helpful in diagnosing problems.
     * <p>
     * This log is accompanied by a log {@code level} parameter and will only
     * be displayed if that log level is less than or equal to the {@link
     * Logger Logger's} current {@code LOG_LEVEL}.
     *
     * @param log   String: The log message to print out.
     * @param level int: The level of this log.
     *
     * @see #init(int)
     */
    public static void logD(final String log,
                            final int level)
    {
        checkInstance();

        processLog(Thread.currentThread().getStackTrace(),
                   E_TYPE.DEBUG,
                   log,
                   level);
    }

    /**
     * Log a {@code DEBUG} message to provide information about program state
     * which may be helpful in diagnosing problems.
     * <p>
     * This log is accompanied by a log {@code tag} parameter and will only be
     * displayed if that log tag has been registered with the {@link Logger}.
     *
     * @param log String: The log message to print out.
     * @param tag String: The tag which must be registered for this log to be
     *            displayed.
     *
     * @see #registerTag(String)
     * @see #registerTags(String[])
     * @see #registerTags(ArrayList)
     */
    public static void logD(final String log,
                            final String tag)
    {
        checkInstance();

        processLog(Thread.currentThread().getStackTrace(),
                   E_TYPE.DEBUG,
                   log,
                   tag);
    }

    /**
     * Log a {@code DEBUG} message to provide information about program state
     * which may be helpful in diagnosing problems.
     * <p>
     * This log is accompanied by a log {@code tag} parameter and will only be
     * displayed if that log tag has been registered with the {@link Logger}.
     * <p>
     * This log is accompanied by a log {@code level} parameter and will only
     * be displayed if that log level is less than or equal to the {@link
     * Logger Logger's} current {@code LOG_LEVEL}.
     *
     * @param log   String: The log message to print out.
     * @param tag   String: The tag which must be registered for this log to be
     *              displayed.
     * @param level int: The level of this log.
     *
     * @see #registerTag(String)
     * @see #registerTags(String[])
     * @see #registerTags(ArrayList)
     * @see #init(int)
     */
    public static void logD(final String log,
                            final String tag,
                            final int level)
    {
        checkInstance();

        processLog(Thread.currentThread().getStackTrace(),
                   E_TYPE.DEBUG,
                   log,
                   tag,
                   level);
    }

    /**
     * Log a {@code WARNING} message to report that something unexpected, but
     * not necessarily problematic, has happened.
     * <p>
     * This log has no accompanying parameters and will always be displayed
     * unless {@code WARNING} logs are disabled.
     *
     * @param log String: The log message to print out.
     */
    public static void logW(final String log)
    {
        checkInstance();

        processLog(Thread.currentThread().getStackTrace(),
                   E_TYPE.WARNING,
                   log);
    }

    /**
     * Log a {@code WARNING} message to report that something unexpected, but
     * not necessarily problematic, has happened.
     * <p>
     * This log is accompanied by a log {@code level} parameter and will only
     * be displayed if that log level is less than or equal to the {@link
     * Logger Logger's} current {@code LOG_LEVEL}.
     *
     * @param log   String: The log message to print out.
     * @param level int: The level of this log.
     *
     * @see #init(int)
     */
    public static void logW(final String log,
                            final int level)
    {
        checkInstance();

        processLog(Thread.currentThread().getStackTrace(),
                   E_TYPE.WARNING,
                   log,
                   level);
    }

    /**
     * Log a {@code WARNING} message to report that something unexpected, but
     * not necessarily problematic, has happened.
     * <p>
     * This log is accompanied by a log {@code tag} parameter and will only be
     * displayed if that log tag has been registered with the {@link Logger}.
     *
     * @param log String: The log message to print out.
     * @param tag String: The tag which must be registered for this log to be
     *            displayed.
     *
     * @see #registerTag(String)
     * @see #registerTags(String[])
     * @see #registerTags(ArrayList)
     */
    public static void logW(final String log,
                            final String tag)
    {
        checkInstance();

        processLog(Thread.currentThread().getStackTrace(),
                   E_TYPE.WARNING,
                   log,
                   tag);
    }

    /**
     * Log a {@code WARNING} message to report that something unexpected, but
     * not necessarily problematic, has happened.
     * <p>
     * This log is accompanied by a log {@code tag} parameter and will only be
     * displayed if that log tag has been registered with the {@link Logger}.
     * <p>
     * This log is accompanied by a log {@code level} parameter and will only
     * be displayed if that log level is less than or equal to the {@link
     * Logger Logger's} current {@code LOG_LEVEL}.
     *
     * @param log   String: The log message to print out.
     * @param tag   String: The tag which must be registered for this log to be
     *              displayed.
     * @param level int: The level of this log.
     *
     * @see #registerTag(String)
     * @see #registerTags(String[])
     * @see #registerTags(ArrayList)
     * @see #init(int)
     */
    public static void logW(final String log,
                            final String tag,
                            final int level)
    {
        checkInstance();

        processLog(Thread.currentThread().getStackTrace(),
                   E_TYPE.WARNING,
                   log,
                   tag,
                   level);
    }

    /**
     * Log an {@code ERROR} message to report that something bad has happened.
     * <p>
     * This log has no accompanying parameters and will always be displayed
     * unless {@code ERROR} logs are disabled.
     *
     * @param log String: The log message to print out.
     */
    public static void logE(final String log)
    {
        checkInstance();

        processLog(Thread.currentThread().getStackTrace(),
                   E_TYPE.ERROR,
                   log);
    }

    /**
     * Log an {@code ERROR} message to report that something bad has happened.
     * <p>
     * This log is accompanied by a log {@code level} parameter and will only
     * be displayed if that log level is less than or equal to the {@link
     * Logger Logger's} current {@code LOG_LEVEL}.
     *
     * @param log   String: The log message to print out.
     * @param level int: The level of this log.
     *
     * @see #init(int)
     */
    public static void logE(final String log,
                            final int level)
    {
        checkInstance();

        processLog(Thread.currentThread().getStackTrace(),
                   E_TYPE.ERROR,
                   log,
                   level);
    }

    /**
     * Log an {@code ERROR} message to report that something bad has happened.
     * <p>
     * This log is accompanied by a log {@code tag} parameter and will only be
     * displayed if that log tag has been registered with the {@link Logger}.
     *
     * @param log String: The log message to print out.
     * @param tag String: The tag which must be registered for this log to be
     *            displayed.
     *
     * @see #registerTag(String)
     * @see #registerTags(String[])
     * @see #registerTags(ArrayList)
     */
    public static void logE(final String log,
                            final String tag)
    {
        checkInstance();

        processLog(Thread.currentThread().getStackTrace(),
                   E_TYPE.ERROR,
                   log,
                   tag);
    }

    /**
     * Log an {@code ERROR} message to report that something bad has happened.
     * <p>
     * This log is accompanied by a log {@code tag} parameter and will only be
     * displayed if that log tag has been registered with the {@link Logger}.
     * <p>
     * This log is accompanied by a log {@code level} parameter and will only
     * be displayed if that log level is less than or equal to the {@link
     * Logger Logger's} current {@code LOG_LEVEL}.
     *
     * @param log   String: The log message to print out.
     * @param tag   String: The tag which must be registered for this log to be
     *              displayed.
     * @param level int: The level of this log.
     *
     * @see #registerTag(String)
     * @see #registerTags(String[])
     * @see #registerTags(ArrayList)
     * @see #init(int)
     */
    public static void logE(final String log,
                            final String tag,
                            final int level)
    {
        checkInstance();

        processLog(Thread.currentThread().getStackTrace(),
                   E_TYPE.ERROR,
                   log,
                   tag,
                   level);
    }
}
