package com.scorpius_enterprises.log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * {@link Logger com.scorpius_enterprises.log.Logger}
 *
 * @author Scorple
 * @since 2017-03-11
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class Logger
{
    private static Logger INSTANCE;

    private static final int _API_STACK_TRACE_DEPTH_ = 2;

    private static int LOG_LEVEL = 0;

    private static final ArrayList<String> _REGISTERED_TAGS_ = new ArrayList<>();

    public enum E_TYPE
    {
        GENERAL,
        INFO,
        DEBUG,
        WARNING,
        ERROR
    }

    private static final ArrayList<E_TYPE> _DISABLED_TYPES_ = new ArrayList<>();

    private Logger()
    {

    }

    static
    {
        INSTANCE = new Logger();
    }

    private static void checkInstance()
    {
        if (null == INSTANCE)
        {
            INSTANCE = new Logger();

            processLog(Thread.currentThread()
                             .getStackTrace()[_API_STACK_TRACE_DEPTH_ + 1],
                       E_TYPE.WARNING,
                       "attempt to log or configure without initializing, created Logger instance "
                       + "with LOG_LEVEL 0");
        }
    }

    /**
     * Must be called to ensure there is an INSTANCE of this otherwise static
     * class such that {@code LOG_LEVEL} and {@code _REGISTERED_TAGS_} data will
     * not be destroyed.
     *
     * @param _log_level_ int: The maximum level of logs accompanied by a {@code
     *                    level} parameter to display.
     */
    public static void init(final int _log_level_)
    {
        if (null == INSTANCE)
        {
            INSTANCE = new Logger();
        }

        Logger.LOG_LEVEL = _log_level_;
    }

    /**
     * May be called to enable a single tag for logging. Log method calls
     * including a tag parameter will only be displayed if that tag is in the
     * list of registered tags.
     *
     * @param _tag_ String: The tag to register.
     */
    public static void registerTag(final String _tag_)
    {
        checkInstance();

        if (!_REGISTERED_TAGS_.contains(_tag_))
        {
            Logger._REGISTERED_TAGS_.add(_tag_);
        }
    }

    /**
     * May be called to enable a list of tags for logging. Log method calls
     * including a tag parameter will only be displayed if that tag is in the
     * list of registered tags.
     *
     * @param _tags_ String[]: The list of tags to register.
     */
    public static void registerTags(final String[] _tags_)
    {
        checkInstance();

        for (final String _tag_ : _tags_)
        {
            if (!_REGISTERED_TAGS_.contains(_tag_))
            {
                Logger._REGISTERED_TAGS_.add(_tag_);
            }
        }
    }

    /**
     * May be called to enable a list of tags for logging. Log method calls
     * including a tag parameter will only be displayed if that tag is in the
     * list of registered tags.
     *
     * @param _tags_ ArrayList: The list of tags to register.
     */
    public static void registerTags(final ArrayList<String> _tags_)
    {
        checkInstance();

        for (final String _tag_ : _tags_)
        {
            if (!_REGISTERED_TAGS_.contains(_tag_))
            {
                Logger._REGISTERED_TAGS_.add(_tag_);
            }
        }
    }

    /**
     * May be called to disable a single tag for logging. Log method calls
     * including a tag parameter will only be displayed if that tag is in the
     * list of registered tags.
     *
     * @param _tag_ String: The tag to deregister.
     */
    public static void deregisterTag(final String _tag_)
    {
        checkInstance();

        if (_REGISTERED_TAGS_.contains(_tag_))
        {
            Logger._REGISTERED_TAGS_.remove(_tag_);
        }
    }

    /**
     * May be called to disable a list of tags for logging. Log method calls
     * including a tag parameter will only be displayed if that tag is in the
     * list of registered tags.
     *
     * @param _tags_ String[]: The list of tags to deregister.
     */
    public static void deregisterTags(final String[] _tags_)
    {
        checkInstance();

        for (final String _tag_ : _tags_)
        {
            if (_REGISTERED_TAGS_.contains(_tag_))
            {
                Logger._REGISTERED_TAGS_.remove(_tag_);
            }
        }
    }

    /**
     * May be called to disable a list of tags for logging. Log method calls
     * including a tag parameter will only be displayed if that tag is in the
     * list of registered tags.
     *
     * @param _tags_ ArrayList: The list of tags to deregister.
     */
    public static void deregisterTags(final ArrayList<String> _tags_)
    {
        checkInstance();

        for (final String _tag_ : _tags_)
        {
            if (_REGISTERED_TAGS_.contains(_tag_))
            {
                Logger._REGISTERED_TAGS_.remove(_tag_);
            }
        }
    }

    /**
     * May be called to prevent a single log type from being displayed. All log
     * types will display by default.
     *
     * @param _type_ E_TYPE: The type of log to prevent from displaying.
     */
    public static void disableType(final E_TYPE _type_)
    {
        checkInstance();

        if (!_DISABLED_TYPES_.contains(_type_))
        {
            _DISABLED_TYPES_.add(_type_);
        }
    }

    /**
     * May be called to prevent any from a list of log types from being
     * displayed. All log types will display by default.
     *
     * @param _types_ E_TYPE[]: The list of log types to prevent from displaying.
     */
    public static void disableTypes(final E_TYPE[] _types_)
    {
        checkInstance();

        for (final E_TYPE _type_ : _types_)
        {
            if (!_DISABLED_TYPES_.contains(_type_))
            {
                _DISABLED_TYPES_.add(_type_);
            }
        }
    }

    /**
     * May be called to prevent any from a list of log types from being
     * displayed. All log types will display by default.
     *
     * @param _types_ ArrayList: The list of log types to prevent from
     *                displaying.
     */
    public static void disableTypes(final ArrayList<E_TYPE> _types_)
    {
        checkInstance();

        for (final E_TYPE _type_ : _types_)
        {
            if (!_DISABLED_TYPES_.contains(_type_))
            {
                _DISABLED_TYPES_.add(_type_);
            }
        }
    }

    /**
     * May be called to undo preventing a log type from being displayed. All
     * log types are enabled by default.
     *
     * @param _type_ E_TYPE: The log type to allow to display.
     */
    public static void enableType(final E_TYPE _type_)
    {
        checkInstance();

        if (_DISABLED_TYPES_.contains(_type_))
        {
            _DISABLED_TYPES_.remove(_type_);
        }
    }

    /**
     * May be called to undo preventing any from a list of log types from being
     * displayed. All log types are enabled by default.
     *
     * @param _types_ E_TYPE[]: The list of log types to allow to display.
     */
    public static void enableTypes(final E_TYPE[] _types_)
    {
        checkInstance();

        for (final E_TYPE _type_ : _types_)
        {
            if (_DISABLED_TYPES_.contains(_type_))
            {
                _DISABLED_TYPES_.remove(_type_);
            }
        }
    }

    /**
     * May be called to undo preventing any from a list of log types from being
     * displayed. All log types are enabled by default.
     *
     * @param _types_ ArrayList: The list of log types to allow to display.
     */
    public static void enableTypes(final ArrayList<E_TYPE> _types_)
    {
        checkInstance();

        for (final E_TYPE _type_ : _types_)
        {
            if (_DISABLED_TYPES_.contains(_type_))
            {
                _DISABLED_TYPES_.remove(_type_);
            }
        }
    }

    private static String getClassName(final StackTraceElement _stackTraceElement_)
    {
        final String[] _splitClassName_ =
            _stackTraceElement_.getClassName()
                               .split("\\.");
        return _splitClassName_[_splitClassName_.length - 1];
    }

    private static String getMethodName(final StackTraceElement _stackTraceElement_)
    {
        return _stackTraceElement_.getMethodName() + "()";
    }

    private static int getLineNumber(final StackTraceElement _stackTraceElement_)
    {
        return _stackTraceElement_.getLineNumber();
    }

    private static void postLog(final StackTraceElement _stackTraceElement_,
                                final String _type_,
                                final String _log_)
    {
        System.out.printf("%s: %s: %s: %d: %s: %s\n",
                          new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()),
                          _type_,
                          getClassName(_stackTraceElement_),
                          getLineNumber(_stackTraceElement_),
                          getMethodName(_stackTraceElement_),
                          _log_);
    }

    private static void processLog(final StackTraceElement _stackTraceElement_,
                                   final E_TYPE _type_,
                                   final String _log_)
    {
        if (!_DISABLED_TYPES_.contains(_type_))
        {
            postLog(_stackTraceElement_,
                    _type_.toString(),
                    _log_);
        }
    }

    private static void processLog(final StackTraceElement _stackTraceElement_,
                                   final E_TYPE _type_,
                                   final int _level_,
                                   final String _log_)
    {
        if (!_DISABLED_TYPES_.contains(_type_)
            && LOG_LEVEL >= _level_)
        {
            postLog(_stackTraceElement_,
                    String.format("%s: %d: ",
                                  _type_.toString(),
                                  _level_),
                    _log_);
        }
    }

    private static void processLog(final StackTraceElement _stackTraceElement_,
                                   final E_TYPE _type_,
                                   final String _tag_,
                                   final String _log_)
    {
        if (_DISABLED_TYPES_.stream()
                            .parallel()
                            .noneMatch(e_type -> e_type.equals(_type_))
            && _REGISTERED_TAGS_.stream()
                                .parallel()
                                .anyMatch(e_tag -> e_tag.equals(_tag_)))
        {
            postLog(_stackTraceElement_,
                    String.format("%s: %s: ",
                                  _type_.toString(),
                                  _tag_),
                    _log_);
        }
    }

    private static void processLog(final StackTraceElement _stackTraceElement_,
                                   final E_TYPE _type_,
                                   final int _level_,
                                   final String _tag_,
                                   final String _log_)
    {
        if (_DISABLED_TYPES_.stream()
                            .parallel()
                            .noneMatch(e_type -> e_type.equals(_type_))
            && LOG_LEVEL >= _level_
            && _REGISTERED_TAGS_.stream()
                                .parallel()
                                .anyMatch(e_tag -> e_tag.equals(_tag_)))
        {
            postLog(_stackTraceElement_,
                    String.format("%s: %d: %s: ",
                                  _type_.toString(),
                                  _level_,
                                  _tag_),
                    _log_);
        }
    }

    /**
     * Log a {@code GENERAL} message with no given purpose.
     * <p>
     * This log has no accompanying parameters and will always be displayed
     * unless {@code GENERAL} logs are disabled.
     *
     * @param _format_ String: The format for the log message to print out.
     * @param _params_ String: Any parameters to the log message to print out.
     */
    public static void log(final String _format_,
                           final Object... _params_)
    {
        checkInstance();

        final String _log_ = String.format(_format_,
                                           _params_);

        processLog(Thread.currentThread()
                         .getStackTrace()[_API_STACK_TRACE_DEPTH_],
                   E_TYPE.GENERAL,
                   _log_);
    }

    /**
     * Log a {@code GENERAL} message with no given purpose.
     * <p>
     * This log is accompanied by a log {@code level} parameter and will only
     * be displayed if that log level is less than or equal to the {@link
     * Logger Logger's} current {@code LOG_LEVEL}.
     *
     * @param _level_  int: The level of this log.
     * @param _format_ String: The format for the log message to print out.
     * @param _params_ String: Any parameters to the log message to print out.
     *
     * @see #init(int)
     */
    public static void log(final int _level_,
                           final String _format_,
                           final Object... _params_)
    {
        checkInstance();

        final String _log_ = String.format(_format_,
                                           _params_);

        processLog(Thread.currentThread()
                         .getStackTrace()[_API_STACK_TRACE_DEPTH_],
                   E_TYPE.GENERAL,
                   _level_,
                   _log_);
    }

    /**
     * Log a {@code GENERAL} message with no given purpose.
     * <p>
     * This log is accompanied by a log {@code tag} parameter and will only be
     * displayed if that log tag has been registered with the {@link Logger}.
     *
     * @param _tag_    String: The tag which must be registered for this log to be
     *                 displayed.
     * @param _format_ String: The format for the log message to print out.
     * @param _params_ String: Any parameters to the log message to print out.
     *
     * @see #registerTag(String)
     * @see #registerTags(String[])
     * @see #registerTags(ArrayList)
     */
    public static void logT(final String _tag_,
                            final String _format_,
                            final Object... _params_)
    {
        checkInstance();

        final String _log_ = String.format(_format_,
                                           _params_);

        processLog(Thread.currentThread()
                         .getStackTrace()[_API_STACK_TRACE_DEPTH_],
                   E_TYPE.GENERAL,
                   _tag_,
                   _log_);
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
     * @param _level_  int: The level of this log.
     * @param _tag_    String: The tag which must be registered for this log to be
     *                 displayed.
     * @param _format_ String: The format for the log message to print out.
     * @param _params_ String: Any parameters to the log message to print out.
     *
     * @see #registerTag(String)
     * @see #registerTags(String[])
     * @see #registerTags(ArrayList)
     * @see #init(int)
     */
    public static void logT(final int _level_,
                            final String _tag_,
                            final String _format_,
                            final Object... _params_)
    {
        checkInstance();

        final String _log_ = String.format(_format_,
                                           _params_);

        processLog(Thread.currentThread()
                         .getStackTrace()[_API_STACK_TRACE_DEPTH_],
                   E_TYPE.GENERAL,
                   _level_,
                   _tag_,
                   _log_);
    }

    /**
     * Log an {@code INFO} message to provide information about program state
     * which may be helpful in understanding program progression and status.
     * <p>
     * This log has no accompanying parameters and will always be displayed
     * unless {@code INFO} logs are disabled.
     *
     * @param _format_ String: The format for the log message to print out.
     * @param _params_ String: Any parameters to the log message to print out.
     */
    public static void logI(final String _format_,
                            final Object... _params_)
    {
        checkInstance();

        final String _log_ = String.format(_format_,
                                           _params_);

        processLog(Thread.currentThread()
                         .getStackTrace()[_API_STACK_TRACE_DEPTH_],
                   E_TYPE.INFO,
                   _log_);
    }

    /**
     * Log an {@code INFO} message to provide information about program state
     * which may be helpful in understanding program progression and status.
     * <p>
     * This log is accompanied by a log {@code level} parameter and will only
     * be displayed if that log level is less than or equal to the {@link
     * Logger Logger's} current {@code LOG_LEVEL}.
     *
     * @param _level_  int: The level of this log.
     * @param _format_ String: The format for the log message to print out.
     * @param _params_ String: Any parameters to the log message to print out.
     *
     * @see #init(int)
     */
    public static void logI(final int _level_,
                            final String _format_,
                            final Object... _params_)
    {
        checkInstance();

        final String _log_ = String.format(_format_,
                                           _params_);

        processLog(Thread.currentThread()
                         .getStackTrace()[_API_STACK_TRACE_DEPTH_],
                   E_TYPE.INFO,
                   _level_,
                   _log_);
    }

    /**
     * Log an {@code INFO} message to provide information about program state
     * which may be helpful in understanding program progression and status.
     * <p>
     * This log is accompanied by a log {@code tag} parameter and will only be
     * displayed if that log tag has been registered with the {@link Logger}.
     *
     * @param _tag_    String: The tag which must be registered for this log to be
     *                 displayed.
     * @param _format_ String: The format for the log message to print out.
     * @param _params_ String: Any parameters to the log message to print out.
     *
     * @see #registerTag(String)
     * @see #registerTags(String[])
     * @see #registerTags(ArrayList)
     */
    public static void logIT(final String _tag_,
                             final String _format_,
                             final Object... _params_)
    {
        checkInstance();

        final String _log_ = String.format(_format_,
                                           _params_);

        processLog(Thread.currentThread()
                         .getStackTrace()[_API_STACK_TRACE_DEPTH_],
                   E_TYPE.INFO,
                   _tag_,
                   _log_);
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
     * @param _level_  int: The level of this log.
     * @param _tag_    String: The tag which must be registered for this log to be
     *                 displayed.
     * @param _format_ String: The format for the log message to print out.
     * @param _params_ String: Any parameters to the log message to print out.
     *
     * @see #registerTag(String)
     * @see #registerTags(String[])
     * @see #registerTags(ArrayList)
     * @see #init(int)
     */
    public static void logIT(final int _level_,
                             final String _tag_,
                             final String _format_,
                             final Object... _params_)
    {
        checkInstance();

        final String _log_ = String.format(_format_,
                                           _params_);

        processLog(Thread.currentThread()
                         .getStackTrace()[_API_STACK_TRACE_DEPTH_],
                   E_TYPE.INFO,
                   _level_,
                   _tag_,
                   _log_);
    }

    /**
     * Log a {@code DEBUG} message to provide information about program state
     * which may be helpful in diagnosing problems.
     * <p>
     * This log has no accompanying parameters and will always be displayed
     * unless {@code DEBUG} logs are disabled.
     *
     * @param _format_ String: The format for the log message to print out.
     * @param _params_ String: Any parameters to the log message to print out.
     */
    public static void logD(final String _format_,
                            final Object... _params_)
    {
        checkInstance();

        final String _log_ = String.format(_format_,
                                           _params_);

        processLog(Thread.currentThread()
                         .getStackTrace()[_API_STACK_TRACE_DEPTH_],
                   E_TYPE.DEBUG,
                   _log_);
    }

    /**
     * Log a {@code DEBUG} message to provide information about program state
     * which may be helpful in diagnosing problems.
     * <p>
     * This log is accompanied by a log {@code level} parameter and will only
     * be displayed if that log level is less than or equal to the {@link
     * Logger Logger's} current {@code LOG_LEVEL}.
     *
     * @param _level_  int: The level of this log.
     * @param _format_ String: The format for the log message to print out.
     * @param _params_ String: Any parameters to the log message to print out.
     *
     * @see #init(int)
     */
    public static void logD(final int _level_,
                            final String _format_,
                            final Object... _params_)
    {
        checkInstance();

        String _log_ = String.format(_format_,
                                     _params_);

        processLog(Thread.currentThread()
                         .getStackTrace()[_API_STACK_TRACE_DEPTH_],
                   E_TYPE.DEBUG,
                   _level_,
                   _log_);
    }

    /**
     * Log a {@code DEBUG} message to provide information about program state
     * which may be helpful in diagnosing problems.
     * <p>
     * This log is accompanied by a log {@code tag} parameter and will only be
     * displayed if that log tag has been registered with the {@link Logger}.
     *
     * @param _tag_    String: The tag which must be registered for this log to be
     *                 displayed.
     * @param _format_ String: The format for the log message to print out.
     * @param _params_ String: Any parameters to the log message to print out.
     *
     * @see #registerTag(String)
     * @see #registerTags(String[])
     * @see #registerTags(ArrayList)
     */
    public static void logDT(final String _tag_,
                             final String _format_,
                             final Object... _params_)
    {
        checkInstance();

        String _log_ = String.format(_format_,
                                     _params_);

        processLog(Thread.currentThread()
                         .getStackTrace()[_API_STACK_TRACE_DEPTH_],
                   E_TYPE.DEBUG,
                   _tag_,
                   _log_);
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
     * @param _level_  int: The level of this log.
     * @param _tag_    String: The tag which must be registered for this log to be
     *                 displayed.
     * @param _format_ String: The format for the log message to print out.
     * @param _params_ String: Any parameters to the log message to print out.
     *
     * @see #registerTag(String)
     * @see #registerTags(String[])
     * @see #registerTags(ArrayList)
     * @see #init(int)
     */
    public static void logDT(final int _level_,
                             final String _tag_,
                             final String _format_,
                             final Object... _params_)
    {
        checkInstance();

        String _log_ = String.format(_format_,
                                     _params_);

        processLog(Thread.currentThread()
                         .getStackTrace()[_API_STACK_TRACE_DEPTH_],
                   E_TYPE.DEBUG,
                   _level_,
                   _tag_,
                   _log_);
    }

    /**
     * Log a {@code WARNING} message to report that something unexpected, but
     * not necessarily problematic, has happened.
     * <p>
     * This log has no accompanying parameters and will always be displayed
     * unless {@code WARNING} logs are disabled.
     *
     * @param _format_ String: The format for the log message to print out.
     * @param _params_ String: Any parameters to the log message to print out.
     */
    public static void logW(final String _format_,
                            final Object... _params_)
    {
        checkInstance();

        final String _log_ = String.format(_format_,
                                           _params_);

        processLog(Thread.currentThread()
                         .getStackTrace()[_API_STACK_TRACE_DEPTH_],
                   E_TYPE.WARNING,
                   _log_);
    }

    /**
     * Log a {@code WARNING} message to report that something unexpected, but
     * not necessarily problematic, has happened.
     * <p>
     * This log is accompanied by a log {@code level} parameter and will only
     * be displayed if that log level is less than or equal to the {@link
     * Logger Logger's} current {@code LOG_LEVEL}.
     *
     * @param _level_  int: The level of this log.
     * @param _format_ String: The format for the log message to print out.
     * @param _params_ String: Any parameters to the log message to print out.
     *
     * @see #init(int)
     */
    public static void logW(final int _level_,
                            final String _format_,
                            final Object... _params_)
    {
        checkInstance();

        String _log_ = String.format(_format_,
                                     _params_);

        processLog(Thread.currentThread()
                         .getStackTrace()[_API_STACK_TRACE_DEPTH_],
                   E_TYPE.WARNING,
                   _level_,
                   _log_);
    }

    /**
     * Log a {@code WARNING} message to report that something unexpected, but
     * not necessarily problematic, has happened.
     * <p>
     * This log is accompanied by a log {@code tag} parameter and will only be
     * displayed if that log tag has been registered with the {@link Logger}.
     *
     * @param _tag_    String: The tag which must be registered for this log to be
     *                 displayed.
     * @param _format_ String: The format for the log message to print out.
     * @param _params_ String: Any parameters to the log message to print out.
     *
     * @see #registerTag(String)
     * @see #registerTags(String[])
     * @see #registerTags(ArrayList)
     */
    public static void logWT(final String _tag_,
                             final String _format_,
                             final Object... _params_)
    {
        checkInstance();

        final String _log_ = String.format(_format_,
                                           _params_);

        processLog(Thread.currentThread()
                         .getStackTrace()[_API_STACK_TRACE_DEPTH_],
                   E_TYPE.WARNING,
                   _tag_,
                   _log_);
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
     * @param _tag_    String: The tag which must be registered for this log to be
     *                 displayed.
     * @param _level_  int: The level of this log.
     * @param _format_ String: The format for the log message to print out.
     * @param _params_ String: Any parameters to the log message to print out.
     *
     * @see #registerTag(String)
     * @see #registerTags(String[])
     * @see #registerTags(ArrayList)
     * @see #init(int)
     */
    public static void logWT(final int _level_,
                             final String _tag_,
                             final String _format_,
                             final Object... _params_)
    {
        checkInstance();

        final String _log_ = String.format(_format_,
                                           _params_);

        processLog(Thread.currentThread()
                         .getStackTrace()[_API_STACK_TRACE_DEPTH_],
                   E_TYPE.WARNING,
                   _level_,
                   _tag_,
                   _log_);
    }

    /**
     * Log an {@code ERROR} message to report that something bad has happened.
     * <p>
     * This log has no accompanying parameters and will always be displayed
     * unless {@code ERROR} logs are disabled.
     *
     * @param _format_ String: The format for the log message to print out.
     * @param _params_ String: Any parameters to the log message to print out.
     */
    public static void logE(final String _format_,
                            final Object... _params_)
    {
        checkInstance();

        final String _log_ = String.format(_format_,
                                           _params_);

        processLog(Thread.currentThread()
                         .getStackTrace()[_API_STACK_TRACE_DEPTH_],
                   E_TYPE.ERROR,
                   _log_);
    }

    /**
     * Log an {@code ERROR} message to report that something bad has happened.
     * <p>
     * This log is accompanied by a log {@code level} parameter and will only
     * be displayed if that log level is less than or equal to the {@link
     * Logger Logger's} current {@code LOG_LEVEL}.
     *
     * @param _level_  int: The level of this log.
     * @param _format_ String: The format for the log message to print out.
     * @param _params_ String: Any parameters to the log message to print out.
     *
     * @see #init(int)
     */
    public static void logE(final int _level_,
                            final String _format_,
                            final Object... _params_)
    {
        checkInstance();

        final String _log_ = String.format(_format_,
                                           _params_);

        processLog(Thread.currentThread()
                         .getStackTrace()[_API_STACK_TRACE_DEPTH_],
                   E_TYPE.ERROR,
                   _level_,
                   _log_);
    }

    /**
     * Log an {@code ERROR} message to report that something bad has happened.
     * <p>
     * This log is accompanied by a log {@code tag} parameter and will only be
     * displayed if that log tag has been registered with the {@link Logger}.
     *
     * @param _tag_    String: The tag which must be registered for this log to be
     *                 displayed.
     * @param _format_ String: The format for the log message to print out.
     * @param _params_ String: Any parameters to the log message to print out.
     *
     * @see #registerTag(String)
     * @see #registerTags(String[])
     * @see #registerTags(ArrayList)
     */
    public static void logET(final String _tag_,
                             final String _format_,
                             final Object... _params_)
    {
        checkInstance();

        final String _log_ = String.format(_format_,
                                           _params_);

        processLog(Thread.currentThread()
                         .getStackTrace()[_API_STACK_TRACE_DEPTH_],
                   E_TYPE.ERROR,
                   _tag_,
                   _log_);
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
     * @param _tag_   String: The tag which must be registered for this log to be
     *                displayed.
     * @param _level_ int: The level of this log.
     *
     * @see #registerTag(String)
     * @see #registerTags(String[])
     * @see #registerTags(ArrayList)
     * @see #init(int)
     */
    public static void logET(final int _level_,
                             final String _tag_,
                             final String _format_,
                             final Object... _params_)
    {
        checkInstance();

        final String _log_ = String.format(_format_,
                                           _params_);

        processLog(Thread.currentThread()
                         .getStackTrace()[_API_STACK_TRACE_DEPTH_],
                   E_TYPE.ERROR,
                   _level_,
                   _tag_,
                   _log_);
    }
}
